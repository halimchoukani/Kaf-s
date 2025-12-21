package com.halimchoukani.kafs.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.halimchoukani.kafs.data.local.database.DatabaseProvider
import com.halimchoukani.kafs.data.repository.UserRepository
import com.halimchoukani.kafs.domain.usecase.SignUpUseCase

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(
        DatabaseProvider.getDatabase(application).userDao()
    )
    private val signUpUseCase = SignUpUseCase(userRepository)

    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun register(email: String, password: String, fullName: String, onSuccess: () -> Unit) {
        loading = true
        signUpUseCase.execute(
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