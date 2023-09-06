package com.emirhanuyunmaz.kotlinnotesmvvm.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.emirhanuyunmaz.kotlinnotesmvvm.Model.Note
import com.emirhanuyunmaz.kotlinnotesmvvm.R
import com.emirhanuyunmaz.kotlinnotesmvvm.databinding.ListItemBinding
import kotlin.random.Random

class NotesAdapter(val listener: notesItemClickListener) :RecyclerView.Adapter<NotesAdapter.NotesVH>() {

    private val NotesList=ArrayList<Note>()
    private val fullist=ArrayList<Note>()

    inner class NotesVH(var binding: ListItemBinding):RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesVH {
        val binding=ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotesVH(binding)
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    override fun onBindViewHolder(holder: NotesVH, position: Int) {
        val currentNote=NotesList[position]
        holder.binding.tvTitle.text=currentNote.title
        holder.binding.tvTitle.isSelected=true

        holder.binding.tvNote.text=currentNote.note
        holder.binding.tvDate.text=currentNote.date
        holder.binding.tvDate.isSelected=true

        holder.binding.cardLayout.setCardBackgroundColor(holder.itemView.resources.getColor(getRandomColor(),null   ))

        holder.binding.cardLayout.setOnClickListener {
            listener.onItemClick(NotesList[holder.adapterPosition])
        }

        holder.binding.cardLayout.setOnLongClickListener {
            listener.onlongItemClick(NotesList[holder.adapterPosition],holder.binding.cardLayout)
            true
        }
    }

    private fun getRandomColor():Int{
        val colors=ArrayList<Int>()
        colors.add(R.color.color1)
        colors.add(R.color.color2)
        colors.add(R.color.color3)
        colors.add(R.color.color4)
        colors.add(R.color.color5)
        colors.add(R.color.color6)

        val seed=System.currentTimeMillis().toInt()
        val randomIndex= Random(seed).nextInt(colors.size)
        return colors[randomIndex]
    }

    interface notesItemClickListener{
        fun onItemClick(note: Note)
        fun onlongItemClick(note: Note,cardView: CardView)
    }

    fun update(newList:List<Note>){

        fullist.clear()
        fullist.addAll(newList)

        NotesList.clear()
        NotesList.addAll(fullist)

        notifyDataSetChanged()
    }

    fun filterlist(search:String){

        NotesList.clear()
        for (item in fullist){

            if(item.title?.lowercase()?.contains(search.lowercase())== true || item.note?.lowercase()?.contains(search.lowercase())==true){
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

}