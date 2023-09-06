package com.emirhanuyunmaz.kotlinnotesmvvm.Database

import androidx.lifecycle.LiveData
import com.emirhanuyunmaz.kotlinnotesmvvm.Model.Note

class NotesRepository(private var noteDao: NoteDao) {

    val allNote :LiveData<List<Note>> =noteDao.getAll()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note.id,note.title!!,note.note!!)
    }

}