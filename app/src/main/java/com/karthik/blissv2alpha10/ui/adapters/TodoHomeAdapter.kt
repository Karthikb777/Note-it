package com.karthik.blissv2alpha10.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karthik.blissv2alpha10.R

class TodoHomeAdapter(private val context: Context): RecyclerView.Adapter<TodoHomeAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.todo_card_text)
        val completed = itemView.findViewById<ImageButton>(R.id.completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val root  = TodoViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_card, parent, false))

        return root
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.title.text = "Lorem ipsum sit amet"
    }

    override fun getItemCount(): Int {
        return 30
    }
}