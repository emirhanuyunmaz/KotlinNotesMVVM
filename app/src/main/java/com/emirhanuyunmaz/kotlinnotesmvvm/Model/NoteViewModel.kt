package com.emirhanuyunmaz.kotlinnotesmvvm.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emirhanuyunmaz.kotlinnotesmvvm.Database.NoteDatabase
import com.emirhanuyunmaz.kotlinnotesmvvm.Database.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application:Application):AndroidViewModel(application) {

    private val repository:NotesRepository
    val allNote:LiveData<List<Note>>

    init {
    val dao=NoteDatabase.getDatabase(application.applicationContext).getDao()
        repository= NotesRepository(dao)
        allNote=repository.allNote
    }

    fun deleteNote(note: Note)= CoroutineScope(Dispatchers.IO).launch {
        repository.delete(note)
    }

    fun insert(note:Note)=CoroutineScope(Dispatchers.IO).launch {
        repository.insert(note)
    }

    fun update(note:Note)=CoroutineScope(Dispatchers.IO).launch {
        repository.update(note)
    }

}