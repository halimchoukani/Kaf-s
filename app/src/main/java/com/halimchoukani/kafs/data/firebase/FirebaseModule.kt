package com.halimchoukani.kafs.data.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object FirebaseModule {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance().reference
}
