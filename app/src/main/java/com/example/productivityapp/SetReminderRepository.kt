package com.example.productivityapp

import android.app.Application

class SetReminderRepository(context: Application) {
    private val remindersDao: RemindersDao = AppDatabase.getDatabase(context).remindersDao()

    suspend fun insertReminder(reminder: Reminder){
        remindersDao.insertReminder(reminder)
    }
}