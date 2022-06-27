package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateNoteViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateNoteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}