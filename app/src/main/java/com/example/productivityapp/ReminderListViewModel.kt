package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderListViewModel(application: Application): AndroidViewModel(application) {
    private val repo: ReminderListRepository = ReminderListRepository(application)
    val reminders: LiveData<List<Reminder>> = repo.getReminders()

    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO){
            repo.deleteReminder(reminder)
        }
    }
}