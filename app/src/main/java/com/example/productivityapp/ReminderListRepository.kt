package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.LiveData

class ReminderListRepository(context: Application) {
    private val remindersDao: RemindersDao = AppDatabase.getDatabase(context).remindersDao()

    fun getReminders(): LiveData<List<Reminder>> {
        return remindersDao.getAllReminders()
    }

    suspend fun deleteReminder(reminder: Reminder){
        remindersDao.deleteReminder(reminder)
    }
}