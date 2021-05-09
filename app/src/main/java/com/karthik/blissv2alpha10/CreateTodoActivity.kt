package com.karthik.blissv2alpha10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.karthik.blissv2alpha10.database.entities.Todo
import com.karthik.blissv2alpha10.ui.viewModels.TodoViewModel
import kotlinx.android.synthetic.main.activity_create_todo.*

class CreateTodoActivity : AppCompatActivity() {

    private var priority = "low"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        val viewModel: TodoViewModel by viewModels()

        priorityHigh.setOnClickListener {
            priority = "high"
//            it.setBackgroundColor()
        }

        priorityMedium.setOnClickListener {
            priority = "medium"
//            it.setBackgroundColor()
        }

        priorityLow.setOnClickListener {
            priority = "low"
//            it.setBackgroundColor()
        }

        todoSaveBtn.setOnClickListener {
            val todoTitle = todoTitle.text.toString()

            viewModel.insertTodo(
                Todo(
                    todoTitle = todoTitle,
                    priority = priority,
                    isCompleted = false
                )
            )
            finish()
        }

        todoCloseBtn.setOnClickListener {
            finish()
        }
    }
}