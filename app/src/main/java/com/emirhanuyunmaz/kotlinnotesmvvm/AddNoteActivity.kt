package com.emirhanuyunmaz.kotlinnotesmvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.emirhanuyunmaz.kotlinnotesmvvm.Model.Note
import com.emirhanuyunmaz.kotlinnotesmvvm.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.logging.SimpleFormatter

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var oldNote: Note
    var isUpdate=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {

            oldNote=intent.getSerializableExtra("current_note") as Note

            binding.etTitle.setText(oldNote.title)
            binding.editTextText2.setText(oldNote.note)
            isUpdate=true


        }catch (e:Exception){
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener{

            val title =binding.etTitle.text.toString()
            val note_desc=binding.editTextText2.text.toString()

            if(title.isNotEmpty() && note_desc.isNotEmpty()){

                val formatter=SimpleDateFormat("EEE , d MMM yyy HH:mm a")

                if(isUpdate){
                    note=Note(
                        oldNote.id,title,note_desc,formatter.format(Date())
                    )
                }else{
                    note=Note(
                        null,title,note_desc,formatter.format(Date())
                    )
                }

                val intent=Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{

                Toast.makeText(this@AddNoteActivity, "Please some data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBack.setOnClickListener{
            onBackPressed()
        }


    }
}