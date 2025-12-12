package com.halimchoukani.kafs.data.model

import java.util.Date

data class Coffee(
    val id:String = "",
    val name: String = "",
    val category: String = "",
    val price: Double = 0.0,
    val priceAfterPromo: Double? = null,
    val imageRes: String = "",
    val createdAt: String = "",
    val description: String = ""
)
