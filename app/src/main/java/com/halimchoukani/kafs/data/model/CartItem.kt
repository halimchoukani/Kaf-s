package com.halimchoukani.kafs.data.model

data class CartItem(
    val coffee: Coffee = Coffee(),
    var quantity: Int = 1
)