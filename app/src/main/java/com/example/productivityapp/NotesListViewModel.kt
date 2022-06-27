package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesListViewModel(application: Application): AndroidViewModel(application) {
    private val repo: NotesListRepository = NotesListRepository(application)
    val notes: LiveData<List<Note>> = repo.getNotes()

    fun deleteNote(note: Note){
        viewModelScope.launch (Dispatchers.IO){
            repo.deleteNote(note)
        }
    }
}