package com.karthik.blissv2alpha10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentFrag: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeViewModel : HomeViewModel by viewModels()

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
//                        TODO: change this to a new todo intent after creating new todo activity
                    val newNoteIntent = Intent(this, CreateEditNoteReminderActivity::class.java)
                    startActivity(newNoteIntent)
                }
            }
        }

        homeViewModel.getCurrent().observe(this, Observer {
//            TODO: use this to remove the search bar fragment while displaying the view fragments
            currentFrag = it
        })

    }
}