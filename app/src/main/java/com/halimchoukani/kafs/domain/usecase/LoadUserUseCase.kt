package com.halimchoukani.kafs.domain.usecase

import com.halimchoukani.kafs.data.model.User
import com.halimchoukani.kafs.data.repository.UserRepository

class LoadUserUseCase {

    fun execute(
        uid: String,
        onResult: (User?) -> Unit
    ) {
        UserRepository.getUser(uid, onResult)
    }
}
