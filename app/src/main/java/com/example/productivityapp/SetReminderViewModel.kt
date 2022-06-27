package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SetReminderViewModel(application: Application): AndroidViewModel(application) {
    private val repo: SetReminderRepository = SetReminderRepository(application)

    fun insertReminder(reminder: Reminder){
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertReminder(reminder)
        }
    }
}