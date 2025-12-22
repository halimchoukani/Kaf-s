package com.halimchoukani.kafs.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.halimchoukani.kafs.data.model.CartItem
import com.halimchoukani.kafs.data.model.Coffee
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromCoffeeList(value: List<Coffee>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<Coffee>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCoffeeList(value: String?): List<Coffee>? {
        if (value == null) return emptyList()
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<Coffee>>() {}.type
            gson.fromJson(value, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    @TypeConverter
    fun fromCartList(value: List<CartItem>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<CartItem>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCartList(value: String?): List<CartItem>? {
        if (value == null) return emptyList()
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<CartItem>>() {}.type
            gson.fromJson(value, type)
        } catch (e: Exception) {
            emptyList()
        }
    }
}