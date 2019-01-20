package com.disanumber.timer.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.disanumber.timer.model.TimerEntity

@Database(entities = arrayOf(TimerEntity::class), version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun timerDao(): TimersDao//u can't call fun directly

    companion object {
        val DATABASE_NAME = "AppDatabase.db"
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(LOCK) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, DATABASE_NAME)
                                .fallbackToDestructiveMigration().build()


                    }
                }
            }

            return instance
        }
    }
}