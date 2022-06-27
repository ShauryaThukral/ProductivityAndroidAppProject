package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateNoteViewModel(application: Application): AndroidViewModel(application) {
    private val repo: CreateNoteRepository = CreateNoteRepository(application)

    fun insertNote(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            repo.insertNote(note)
        }
    }
}