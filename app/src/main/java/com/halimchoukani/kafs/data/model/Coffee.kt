package com.halimchoukani.kafs.data.model

import java.util.Date

data class Coffee(
    val id:String,
    val name: String,
    val category: String,
    val price: Double,
    val priceAfterPromo: Double? = null,
    val imageRes: String,
    val createdAt: Date = Date(),
    val description: String = ""
)
