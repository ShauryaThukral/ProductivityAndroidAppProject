package com.example.productivityapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.math.min


class NoteAdapter(private val listener2: (Note) -> Unit,private val listener: (Long, String, String) -> Unit): ListAdapter<Note,NoteAdapter.ViewHolder>(DiffCallbackNote()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener{
                listener.invoke(getItem(adapterPosition).createdAt,getItem(adapterPosition).title,getItem(adapterPosition).content)
            }

            itemView.delete.setOnClickListener{
                listener2.invoke(getItem(adapterPosition))
            }
        }

        fun bind(note: Note){
            itemView.title.text = note.title
            val glimpse: String = note.content.substring(0,min(50,note.content.length ))
            itemView.content.text = glimpse
        }
    }
}

class DiffCallbackNote : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.createdAt == newItem.createdAt
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}