package com.halimchoukani.kafs.domain.usecase

import com.halimchoukani.kafs.data.firebase.FirebaseModule

class LoginUseCase {
    fun execute(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        FirebaseModule.auth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFail(e.message ?: "Login failed")
            }
    }
}