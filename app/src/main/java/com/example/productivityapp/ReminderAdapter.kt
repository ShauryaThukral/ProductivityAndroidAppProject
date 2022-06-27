package com.example.productivityapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class ReminderAdapter(private val listener2: (Reminder) -> Unit ,private val listener: (Int,String,String) -> Unit): ListAdapter<Reminder,ReminderAdapter.ViewHolder>(DiffCallbackReminder()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition).requestCode,getItem(adapterPosition).title,getItem(adapterPosition).date)
            }

            itemView.delete.setOnClickListener{
                listener2.invoke(getItem(adapterPosition))
            }
        }

        fun bind(reminder :Reminder){
            itemView.title.text = reminder.title
            itemView.content.text = reminder.date

            if(reminder.reminderTime < System.currentTimeMillis()){
                itemView.title.setTextColor(Color.parseColor("#d7dae0"))
                itemView.content.setTextColor(Color.parseColor("#d7dae0"))
            }
        }
    }
}

class DiffCallbackReminder : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.requestCode == newItem.requestCode
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem == newItem
    }
}