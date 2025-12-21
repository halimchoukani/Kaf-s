package com.halimchoukani.kafs.viewmodel

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.halimchoukani.kafs.data.local.database.DatabaseProvider
import com.halimchoukani.kafs.data.model.User
import com.halimchoukani.kafs.data.repository.UserRepository
import com.halimchoukani.kafs.domain.usecase.LoadUserUseCase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    val userName: State<String> = derivedStateOf { _user.value?.fullName ?: "User" }

    private val auth = FirebaseAuth.getInstance()
    
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
                    
                    // Sync locally if fetched from remote
                    fetchedUser?.let {
                        viewModelScope.launch {
                            userRepository.saveUserLocally(it)
                        }
                    }
                }
            }
        }
    }

    fun updateUser(updatedUser: User, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        viewModelScope.launch {
            // 1. Update Firebase
            userRepository.saveUserToFirebase(updatedUser, {
                // 2. Update Local Room DB
                viewModelScope.launch {
                    userRepository.saveUserLocally(updatedUser)
                    _user.value = updatedUser
                    onSuccess()
                }
            }, onFail)
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