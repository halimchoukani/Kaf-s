package com.halimchoukani.kafs.data.repository

import com.halimchoukani.kafs.data.firebase.FirebaseModule
import com.halimchoukani.kafs.data.model.Coffee
import com.halimchoukani.kafs.data.model.User

object CoffeeRepository {
    private val db = FirebaseModule.db.child("coffees")

    fun getCoffees(onResult: (List<Coffee>?) -> Unit) {
        db
            .get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.children.mapNotNull { child ->
                    child.getValue(Coffee::class.java)
                }
                onResult(list)
            }
            .addOnFailureListener {
                onResult(null)
            }
    }
}