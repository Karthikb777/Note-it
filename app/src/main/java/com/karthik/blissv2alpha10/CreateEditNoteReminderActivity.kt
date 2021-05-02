package com.karthik.blissv2alpha10

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.activity_create_edit_note_reminder.*

class CreateEditNoteReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_note_reminder)

        val viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(NoteViewModel::class.java)

        saveBtn.setOnClickListener {
            val title = noteTitle.text.toString()
            val content = noteContent.text.toString()
            viewModel.insertNote(
                    NoteReminder(title = title, content = content, "", "", "", "")
            )
            finish()
        }

        closeBtn.setOnClickListener {
            finish()
        }
    }
}