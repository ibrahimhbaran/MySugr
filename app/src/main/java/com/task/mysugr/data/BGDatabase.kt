package com.task.mysugr.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BloodGlucose::class], version = 1)
abstract class BGDatabase  : RoomDatabase() {

    abstract fun bloodGlucoseDao(): BloodGlucoseDao

    companion object {

        @Volatile
        private var INSTANCE: BGDatabase? = null

        private lateinit var appContext: Context

        fun getInstance(context: Context): BGDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): BGDatabase {
            appContext = context
            return Room.databaseBuilder(
                context.applicationContext,
                BGDatabase::class.java, "BGDatabase.db"
            ).build()
        }
    }
}