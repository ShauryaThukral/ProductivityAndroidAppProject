package com.example.productivityapp

import android.app.Application
import androidx.lifecycle.LiveData

class CreateNoteRepository(context: Application) {
    private val notesDao: NotesDao = AppDatabase.getDatabase(context).notesDao()

   suspend fun insertNote(note: Note){
        notesDao.insert(note)
   }
}