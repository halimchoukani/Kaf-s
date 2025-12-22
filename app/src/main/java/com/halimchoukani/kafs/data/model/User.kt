package com.halimchoukani.kafs.data.model

import java.util.Date

data class User(
    val id :String = "",
    val email: String ="",
    val fullName: String="",
    val address: String="",
    val favList:List<Coffee> = arrayListOf(),
    val cart: List<CartItem> = arrayListOf(),
    val createdAt: Date = Date(),
)