package com.halimchoukani.kafs.domain.usecase

import com.halimchoukani.kafs.data.model.User
import com.halimchoukani.kafs.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadUserUseCase(private val userRepository: UserRepository) {

    suspend fun execute(
        uid: String,
        onResult: (User?) -> Unit
    ) {
        // 1. Try to load from local Room DB first for speed/offline support
        val localUser = userRepository.getUserFromLocal(uid)
        
        if (localUser != null) {
            onResult(localUser)
        }
    }
}