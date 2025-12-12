package com.halimchoukani.kafs.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.halimchoukani.kafs.data.model.User
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    // The current logged-in user
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    // Expose username directly for convenience
    val userName: State<String> = derivedStateOf { _user.value?.fullName ?: "User" }

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference.child("users")

    init {
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            viewModelScope.launch {
                db.child(uid).get()
                    .addOnSuccessListener { snapshot ->
                        val fetchedUser = snapshot.getValue(User::class.java)
                        _user.value = fetchedUser
                    }
                    .addOnFailureListener {
                        _user.value = null // failed to fetch user
                    }
            }
        }
    }

    // Optional: refresh user manually
    fun refreshUser() {
        loadCurrentUser()
    }

    // Optional: logout
    fun logout(onComplete: () -> Unit) {
        auth.signOut()
        _user.value = null
        onComplete()
    }
}
