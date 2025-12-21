package com.halimchoukani.kafs.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.halimchoukani.kafs.data.model.Coffee
import java.util.Date


@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey
    val id: String = "",
    val email: String ="",
    val fullName: String="",
    val address: String="",
    val favList: List<Coffee> = arrayListOf(),
    val createdAt: Date = Date()
)