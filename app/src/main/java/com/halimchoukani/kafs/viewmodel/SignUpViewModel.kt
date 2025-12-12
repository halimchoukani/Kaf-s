package com.halimchoukani.kafs.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.halimchoukani.kafs.domain.usecase.SignUpUseCase

class SignUpViewModel : ViewModel() {

    private val signUp = SignUpUseCase()

    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun register(email: String, password: String, fullName: String, onSuccess: () -> Unit) {
        loading = true
        signUp.execute(
            email = email,
            password = password,
            fullName = fullName,
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
