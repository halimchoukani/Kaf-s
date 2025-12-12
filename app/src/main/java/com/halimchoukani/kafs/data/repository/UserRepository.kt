package com.halimchoukani.kafs.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.halimchoukani.kafs.data.firebase.FirebaseModule
import com.halimchoukani.kafs.data.model.User

object UserRepository {

    private val auth = FirebaseModule.auth
    private val db = FirebaseModule.db.child("users")

    fun createUser(user: User, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        db.child(user.id)
            .setValue(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFail(e.message ?: "Unknown DB error") }
    }

    fun getUser(uid: String, onResult: (User?) -> Unit) {
        db.child(uid)
            .get()
            .addOnSuccessListener { snapshot ->
                onResult(snapshot.getValue(User::class.java))
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}


