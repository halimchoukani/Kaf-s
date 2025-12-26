package com.halimchoukani.kafs.data.model

import java.util.Date

data class Order(
    val id: String = "",
    val userId: String = "",
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val address: String = "",
    val paymentMethod: String = "",
    val status: String = "Pending",
    val createdAt: Date = Date()
)