package com.emirhanuyunmaz.kotlinnotesmvvm.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emirhanuyunmaz.kotlinnotesmvvm.Model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(note: Note)

    @Delete
     fun delete(note: Note)

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAll():LiveData<List<Note>>

    @Query("UPDATE note_table SET  note=:note , title=:title WHERE id=:id ")
     fun update(id:Int?,title:String,note:String)

}