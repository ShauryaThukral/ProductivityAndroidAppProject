package com.example.productivityapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class,Reminder::class],version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun notesDao() : NotesDao
    abstract fun remindersDao() : RemindersDao

    companion object{
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context) = instance
            ?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build().also { instance = it }
            }
    }
}