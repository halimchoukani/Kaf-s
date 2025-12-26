package com.halimchoukani.kafs.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.halimchoukani.kafs.data.firebase.FirebaseModule
import com.halimchoukani.kafs.data.local.database.DatabaseProvider
import com.halimchoukani.kafs.data.model.CartItem
import com.halimchoukani.kafs.data.model.Coffee
import com.halimchoukani.kafs.data.model.Order
import com.halimchoukani.kafs.data.model.User
import com.halimchoukani.kafs.data.repository.UserRepository
import com.halimchoukani.kafs.domain.usecase.LoadUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    private val _orders = mutableStateListOf<Order>()
    val orders: List<Order> = _orders

    val userName: State<String> = derivedStateOf { _user.value?.fullName ?: "User" }

    private val auth = FirebaseAuth.getInstance()
    private val ordersDb = FirebaseModule.db.child("orders")
    
    // Repository and UseCase setup
    private val userRepository: UserRepository by lazy {
        val database = DatabaseProvider.getDatabase(application)
        UserRepository(database.userDao())
    }
    
    private val loadUserUseCase: LoadUserUseCase by lazy {
        LoadUserUseCase(userRepository)
    }

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            viewModelScope.launch {
                loadUserUseCase.execute(uid) { fetchedUser ->
                    _user.value = fetchedUser
                    fetchOrders(uid)
                    
                    // Sync locally if fetched from remote
                    fetchedUser?.let {
                        viewModelScope.launch {
                            try {
                                userRepository.saveUserLocally(it)
                            } catch (e: Exception) {
                                Log.e("UserViewModel", "Failed to sync user locally", e)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun fetchOrders(uid: String) {
        ordersDb.orderByChild("userId").equalTo(uid).get().addOnSuccessListener { snapshot ->
            val fetchedOrders = snapshot.children.mapNotNull { it.getValue(Order::class.java) }
            _orders.clear()
            _orders.addAll(fetchedOrders.sortedByDescending { it.createdAt })
        }
    }

    fun placeOrder(order: Order, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        ordersDb.child(order.id).setValue(order).addOnSuccessListener {
            // Clear cart after order
            clearCart()
            _orders.add(0, order)
            onSuccess()
        }.addOnFailureListener {
            onFail(it.message ?: "Failed to place order")
        }
    }

    /**
     * Toggles a coffee in the user's favorite list.
     */
    fun toggleFavorite(coffee: Coffee) {
        val currentUser = _user.value ?: return
        val updatedFavList = currentUser.favList.toMutableList()
        val isFavorite = updatedFavList.any { it.id == coffee.id }

        if (isFavorite) {
            updatedFavList.removeAll { it.id == coffee.id }
        } else {
            updatedFavList.add(coffee)
        }

        updateUser(
            currentUser.copy(favList = updatedFavList),
            onSuccess = {
                val message = if (isFavorite) "Removed from favorites" else "Added to favorites"
                Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
            },
            onFail = {
                Toast.makeText(getApplication(), "Failed to update favorites", Toast.LENGTH_SHORT).show()
            }
        )
    }

    fun isFavorite(coffeeId: String): Boolean {
        return _user.value?.favList?.any { it.id == coffeeId } ?: false
    }

    // --- Cart Logic ---

    fun addToCart(coffee: Coffee) {
        val currentUser = _user.value ?: return

        val updatedCart = currentUser.cart
            .map {
                if (it.coffee.id == coffee.id) {
                    it.copy(quantity = it.quantity + 1)
                } else {
                    it
                }
            }
            .let { cart ->
                if (cart.any { it.coffee.id == coffee.id }) {
                    cart
                } else {
                    cart + CartItem(coffee = coffee, quantity = 1)
                }
            }
        
        val updatedUser = currentUser.copy(cart = updatedCart)
        updateUser(
            updatedUser,
            onSuccess = {
                Toast.makeText(
                    getApplication(),
                    "${coffee.name} added to cart",
                    Toast.LENGTH_SHORT
                ).show()
            },
            onFail = {
                Toast.makeText(
                    getApplication(),
                    "Failed to add to cart",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }


    fun removeFromCart(cartItem: CartItem) {
        val currentUser = _user.value ?: return
        val currentCart = currentUser.cart.toMutableList()
        currentCart.removeAll { it.coffee.id == cartItem.coffee.id }

        updateUser(
            currentUser.copy(cart = currentCart),
            onSuccess = { },
            onFail = { }
        )
    }

    fun updateCartItemQuantity(cartItem: CartItem, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeFromCart(cartItem)
            return
        }
        val currentUser = _user.value ?: return
        val currentCart = currentUser.cart.toMutableList()
        val index = currentCart.indexOfFirst { it.coffee.id == cartItem.coffee.id }
        if (index != -1) {
            currentCart[index] = currentCart[index].copy(quantity = newQuantity)
            updateUser(
                currentUser.copy(cart = currentCart),
                onSuccess = { },
                onFail = { }
            )
        }
    }

    fun clearCart() {
        val currentUser = _user.value ?: return
        updateUser(
            currentUser.copy(cart = emptyList()),
            onSuccess = { },
            onFail = { }
        )
    }

    fun updateUser(
        updatedUser: User,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // 1. Update state immediately for UI responsiveness
                _user.value = updatedUser
                
                // 2. Persist to local database on IO thread
                withContext(Dispatchers.IO) {
                    userRepository.saveUserLocally(updatedUser)
                }
                
                // 3. Sync to Firebase in the background
                userRepository.saveUserToFirebase(updatedUser, {
                    onSuccess()
                }, { error ->
                    Log.e("UserViewModel", "Firebase sync failed: $error")
                })
                
            } catch (e: Exception) {
                Log.e("UserViewModel", "Update failed", e)
                onFail(e.message ?: "Failed to update locally")
            }
        }
    }


    fun refreshUser() {
        loadCurrentUser()
    }

    fun logout(onComplete: () -> Unit) {
        auth.signOut()
        _user.value = null
        onComplete()
    }
}