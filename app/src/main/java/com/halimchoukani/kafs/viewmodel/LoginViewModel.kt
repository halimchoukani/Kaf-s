package com.halimchoukani.kafs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.halimchoukani.kafs.domain.usecase.LoginUseCase

class LoginViewModel : ViewModel() {

    private val signIn = LoginUseCase()

    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        loading = true
        signIn.execute(
            email,
            password,
            onSuccess = {
                loading = false
                onSuccess()
            },
            onFail = {
                loading = false
                error = it
            }
        )
    }
}
