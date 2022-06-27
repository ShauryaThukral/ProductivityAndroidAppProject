package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReminderListViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReminderListViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}