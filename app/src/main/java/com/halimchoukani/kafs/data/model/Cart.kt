package com.halimchoukani.kafs.data.model

data class Cart(
    val userId:String = "",
    val itemList: List<Coffee> = arrayListOf()
)
