package com.emirhanuyunmaz.kotlinnotesmvvm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.emirhanuyunmaz.kotlinnotesmvvm.Adapter.NotesAdapter
import com.emirhanuyunmaz.kotlinnotesmvvm.Database.NoteDatabase
import com.emirhanuyunmaz.kotlinnotesmvvm.Model.Note
import com.emirhanuyunmaz.kotlinnotesmvvm.Model.NoteViewModel
import com.emirhanuyunmaz.kotlinnotesmvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),PopupMenu.OnMenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database:NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note
    private val updateNotes=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->

        if(result.resultCode== Activity.RESULT_OK){
            val note=result.data?.getSerializableExtra("note") as? Note
            if(note != null){
                viewModel.update(note)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter=NotesAdapter(object : NotesAdapter.notesItemClickListener{
            override fun onItemClick(note: Note) {
                val intent =Intent(this@MainActivity,AddNoteActivity::class.java)
                intent.putExtra("current_note",note)
                updateNotes.launch(intent)            }

            override fun onlongItemClick(note: Note, cardView: CardView) {
                selectedNote=note
                poupDisplay(cardView)
            }
        })

        initUI()
        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNote.observe(this){list->
            list?.let {
                adapter.update(list)
            }

        }

        database=NoteDatabase.getDatabase(this.applicationContext)

    }

    private fun initUI() {
        binding.recyclerViewNotes.setHasFixedSize(true)
        binding.recyclerViewNotes.layoutManager=StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        binding.recyclerViewNotes.adapter=adapter

        val getContent=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->

            if(result.resultCode== RESULT_OK){
                val note=result.data?.getSerializableExtra("note") as Note
                if(note !=null){
                    viewModel.insert(note)
                }

            }

        }

        binding.fabAddNote.setOnClickListener(){

            val intent= Intent(this@MainActivity,AddNoteActivity::class.java)
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!= null){
                    adapter.filterlist(newText)
                }
                return true
            }

        })

    }

    private fun poupDisplay(cardView: CardView) {
        val poup=PopupMenu(this,cardView)
        poup.setOnMenuItemClickListener(this@MainActivity)
        poup.inflate(R.menu.popup_menu)
        poup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        if(item?.itemId==R.id.poup_delete){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false

    }
}