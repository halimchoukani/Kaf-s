package com.halimchoukani.kafs.data.repository

import com.halimchoukani.kafs.data.firebase.FirebaseModule
import com.halimchoukani.kafs.data.local.dao.UserDao
import com.halimchoukani.kafs.data.local.entity.UserEntity
import com.halimchoukani.kafs.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    private val auth = FirebaseModule.auth
    private val db = FirebaseModule.db.child("users")

    /**
     * Creates or updates a user in Firebase Realtime Database.
     */
    fun saveUserToFirebase(user: User, onSuccess: () -> Unit, onFail: (String) -> Unit) {
        db.child(user.id)
            .setValue(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFail(e.message ?: "Unknown DB error") }
    }

    /**
     * Fetches a user from Firebase Realtime Database.
     */
    fun getUserFromFirebase(uid: String, onResult: (User?) -> Unit) {
        db.child(uid).get().addOnSuccessListener { snapshot ->
            val user = snapshot.getValue(User::class.java)
            onResult(user)
        }.addOnFailureListener {
            onResult(null)
        }
    }

    suspend fun updateUser(user: User) = withContext(Dispatchers.IO){
        userDao.update(user.toEntity())
    }

    /**
     * Retrieves a user from the local Room database.
     */
    suspend fun getUserFromLocal(uid: String): User? = withContext(Dispatchers.IO) {
        userDao.getUserById(uid)?.toModel()
    }

    /**
     * Saves or updates a user in the local Room database.
     */
    suspend fun saveUserLocally(user: User) = withContext(Dispatchers.IO) {
        userDao.insert(user.toEntity())
    }

    /**
     * Deletes a user from the local Room database.
     */
    suspend fun deleteUserLocally(user: User) = withContext(Dispatchers.IO) {
        userDao.delete(user.toEntity())
    }

    // --- Mappers ---

    private fun User.toEntity(): UserEntity {
        return UserEntity(
            id = id,
            email = email,
            fullName = fullName,
            address = address,
            favList = favList,
            cart = cart,
            createdAt = createdAt
        )
    }

    private fun UserEntity.toModel(): User {
        return User(
            id = id,
            email = email,
            fullName = fullName,
            address = address,
            favList = favList,
            cart = cart,
            createdAt = createdAt
        )
    }
}