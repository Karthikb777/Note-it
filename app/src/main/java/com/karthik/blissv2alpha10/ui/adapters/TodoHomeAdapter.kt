package com.karthik.blissv2alpha10.ui.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.database.entities.Todo
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.TodoViewModel

class TodoHomeAdapter(private val context: Context, private val viewModel: TodoViewModel, private val homeViewModel: HomeViewModel) : RecyclerView.Adapter<TodoHomeAdapter.TodoViewHolder>() {

    private val allTodos: ArrayList<Todo> = ArrayList()

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.todo_card_text)
        val priorityDot = itemView.findViewById<ImageButton>(R.id.dot)
        val completed = itemView.findViewById<ImageButton>(R.id.completed)
        val deleteBtn = itemView.findViewById<ImageButton>(R.id.todoDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val root  = TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_card, parent, false))

        root.completed.setOnClickListener {
            val todo = allTodos[root.adapterPosition]
            todo.isCompleted = !todo.isCompleted
            viewModel.updateTodo(todo)
            notifyItemChanged(root.adapterPosition)
            homeViewModel.setSearch(0)
        }

        root.deleteBtn.setOnClickListener {
            viewModel.deleteTodo(allTodos[root.adapterPosition])
            notifyItemRemoved(root.adapterPosition)
            homeViewModel.setSearch(0)
        }
        return root
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.title.text = allTodos[position].todoTitle
        when(allTodos[position].priority) {
            "high" -> holder.priorityDot.setColorFilter(ContextCompat.getColor(context, R.color.red_tint))
            "medium" -> holder.priorityDot.setColorFilter(ContextCompat.getColor(context, R.color.yellow_tint))
            "low" -> holder.priorityDot.setColorFilter(ContextCompat.getColor(context, R.color.green_tint))
        }
        if (allTodos[position].isCompleted) {
            holder.title.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.completed.setImageResource(R.drawable.ic_check_box)
        } else {
            holder.title.paintFlags = Paint.ANTI_ALIAS_FLAG
            holder.completed.setImageResource(R.drawable.ic_check_box_outline)
        }
    }

    override fun getItemCount(): Int {
        return allTodos.size
    }

    fun updateList(newList: List<Todo>?) {
        if (newList != null) {
            allTodos.clear()
            allTodos.addAll(newList.reversed())
        }
        notifyDataSetChanged()
    }
}