package com.emirhanuyunmaz.kotlinnotesmvvm.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.emirhanuyunmaz.kotlinnotesmvvm.Model.Note
import com.emirhanuyunmaz.kotlinnotesmvvm.Utilities.DATABASE_NAME

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase :RoomDatabase(){

    abstract fun getDao():NoteDao
    //Singleton
    companion object{

        @Volatile
        private var INSTANCE :NoteDatabase?=null

        fun getDatabase(context: Context):NoteDatabase{
            return INSTANCE ?: synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,NoteDatabase::class.java,DATABASE_NAME).build()
                INSTANCE=instance
                instance
            }
        }

    }
}