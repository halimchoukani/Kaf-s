package com.halimchoukani.kafs.domain.usecase

import com.halimchoukani.kafs.data.firebase.FirebaseModule
import com.halimchoukani.kafs.data.model.User
import com.halimchoukani.kafs.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpUseCase(private val userRepository: UserRepository) {

    fun execute(
        email: String,
        password: String,
        fullName: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        // 1. Authenticate with Firebase Auth
        FirebaseModule.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid ?: return@addOnSuccessListener onFail("Auth failed: No UID")

                val user = User(
                    id = uid,
                    email = email,
                    fullName = fullName
                )

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        userRepository.saveUserLocally(user)
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    } catch (e: Exception) {
                        // Even if local save fails, we might want to continue since Firebase succeeded
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                onFail(e.message ?: "Authentication error")
            }
    }
}