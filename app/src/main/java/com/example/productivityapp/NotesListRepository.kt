package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.LiveData

class NotesListRepository(context: Application) {
    private val notesDao: NotesDao = AppDatabase.getDatabase(context).notesDao()

    fun getNotes():  LiveData<List<Note>> {
        return notesDao.getAllNotes()
    }

   suspend fun deleteNote(note :Note){
        notesDao.deleteNote(note)
    }
}