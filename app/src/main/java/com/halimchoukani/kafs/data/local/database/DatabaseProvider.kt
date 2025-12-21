package com.halimchoukani.kafs.data.local.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: RoomAppDatabase? = null

    fun getDatabase(context: Context): RoomAppDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                RoomAppDatabase::class.java,
                "kafes"
            ).build().also { INSTANCE = it }
        }
    }
}
