package com.halimchoukani.kafs.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.halimchoukani.kafs.data.firebase.FirebaseModule

class SplashViewModel : ViewModel() {

    val isLoggedIn = mutableStateOf<Boolean?>(null)

    init {
        val user = FirebaseModule.auth.currentUser
        isLoggedIn.value = user != null
    }
}
