package com.halimchoukani.kafs.data.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        val gson = Gson()
        val type = object : TypeToken<List<Coffee>>() {}.type
        return gson.fromJson(value, type)
    }
}