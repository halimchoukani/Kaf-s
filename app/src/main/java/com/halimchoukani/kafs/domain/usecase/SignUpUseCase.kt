package com.halimchoukani.kafs.domain.usecase

import com.halimchoukani.kafs.data.firebase.FirebaseModule
import com.halimchoukani.kafs.data.model.User
import com.halimchoukani.kafs.data.repository.UserRepository

class SignUpUseCase {

    fun execute(
        email: String,
        password: String,
        fullName: String,
        onSuccess: () -> Unit,
        onFail: (String) -> Unit
    ) {
        FirebaseModule.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->

                val uid = result.user?.uid
                if (uid == null) {
                    onFail("UID is null")
                    return@addOnSuccessListener
                }

                val user = User(
                    id = uid,
                    email = email,
                    fullName = fullName
                )

                UserRepository.createUser(
                    user = user,
                    onSuccess = onSuccess,
                    onFail = onFail
                )
            }
            .addOnFailureListener { e ->
                onFail(e.message ?: "Auth error")
            }
    }
}
