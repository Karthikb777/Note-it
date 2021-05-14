package com.karthik.blissv2alpha10

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.karthik.blissv2alpha10.ui.NoteHomeLayout
import com.karthik.blissv2alpha10.ui.ReminderHomeLayout
import com.karthik.blissv2alpha10.ui.TodoHomeLayout
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import com.karthik.blissv2alpha10.ui.viewModels.ReminderViewModel
import com.karthik.blissv2alpha10.ui.viewModels.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private var currentFrag: Int = 0
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeViewModel : HomeViewModel by viewModels()
        val noteViewModel : NoteViewModel by viewModels()
        val todoViewModel : TodoViewModel by viewModels()
        val reminderViewModel : ReminderViewModel by viewModels()

        if (resources.configuration.isNightModeActive) {
            fabAdd.setColorFilter(ContextCompat.getColor(this, R.color.yellow_200))
        }

        fabAdd.setOnClickListener {
            when(currentFrag) {
                0 -> {
                    val newNoteIntent = Intent(this, CreateEditNoteReminderActivity::class.java)
                    startActivity(newNoteIntent)
                }
                1 -> {
                    val newReminderIntent = Intent(this, CreateEditNoteReminderActivity::class.java)
                    startActivity(newReminderIntent)
                }
                2 -> {
                    val newTodoIntent = Intent(this, CreateTodoActivity::class.java)
                    startActivity(newTodoIntent)
                }

            }
        }

        homeViewModel.getCurrent().observe(this, Observer {
            currentFrag = it
            if(it == -1) {
                fabAdd.isVisible = false
                fabAdd.isClickable = false
            }
            else {
                fabAdd.isVisible = true
                fabAdd.isClickable = true
            }
        })

    }
}