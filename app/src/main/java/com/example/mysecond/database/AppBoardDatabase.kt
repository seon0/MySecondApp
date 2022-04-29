package com.example.mysecond.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mysecond.dao.BoardPostDao
import com.example.mysecond.data.BoardPost

@Database(entities = [BoardPost::class ], version = 1)
abstract class AppBoardDatabase : RoomDatabase() {

    abstract fun boardPostDao(): BoardPostDao

    companion object {

        private var INSTANCE: AppBoardDatabase? = null

        fun getDatabase(context: Context): AppBoardDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppBoardDatabase::class.java, "second_app"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }
}