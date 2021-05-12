package com.karthik.blissv2alpha10

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.karthik.blissv2alpha10.database.entities.Todo
import com.karthik.blissv2alpha10.ui.viewModels.TodoViewModel
import kotlinx.android.synthetic.main.activity_create_todo.*

class CreateTodoActivity : AppCompatActivity() {

    private var priority = "low"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_todo)

        val priorityHighCard = findViewById<MaterialCardView>(R.id.priorityHigh)
        val priorityMediumCard = findViewById<MaterialCardView>(R.id.priorityMedium)
        val priorityLowCard = findViewById<MaterialCardView>(R.id.priorityLow)

//        getting the card bg color from theme
        val typedValue = TypedValue()
        val theme = theme
        theme.resolveAttribute(R.attr.cardBackgroundColor, typedValue, true)
        @ColorInt val colorBg = typedValue.data

        val viewModel: TodoViewModel by viewModels()

        priorityHighCard.setOnClickListener {
            priority = "high"
            priorityHighCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red_tint))
            priorityMedium.setCardBackgroundColor(colorBg)
            priorityLow.setCardBackgroundColor(colorBg)
            priorityHighCard.alpha = 0.7F
        }

        priorityMediumCard.setOnClickListener {
            priority = "medium"
            priorityMediumCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.yellow_tint))
            priorityHigh.setCardBackgroundColor(colorBg)
            priorityLow.setCardBackgroundColor(colorBg)
            priorityMediumCard.alpha = 0.7F
        }

        priorityLowCard.setOnClickListener {
            priority = "low"
            priorityLowCard.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green_tint))
            priorityMedium.setCardBackgroundColor(colorBg)
            priorityHigh.setCardBackgroundColor(colorBg)
            priorityLowCard.alpha = 0.7F

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