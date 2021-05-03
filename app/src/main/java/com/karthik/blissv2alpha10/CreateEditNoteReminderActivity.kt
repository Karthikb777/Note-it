package com.karthik.blissv2alpha10

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.activity_create_edit_note_reminder.*

class CreateEditNoteReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_note_reminder)

        val id = intent.getIntExtra("com.karthik.notesApp.ID", 0)
        val title = intent.getStringExtra("com.karthik.notesApp.TITLE")

        var toBeEdited = false

        val viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(Application())).get(NoteViewModel::class.java)

        if (id != 0 && title!!.isNotEmpty()) {
            viewModel.getNoteByTitle(title)
            viewModel.gotNote.observe(this, Observer {
                noteTitle.setText(it[0].title)
                noteContent.setText(it[0].content)
                toBeEdited = true
            })
        }

        saveBtn.setOnClickListener {
            if (noteTitle.text.isNotEmpty() && noteContent.text.isNotEmpty()) {
                val title = noteTitle.text.toString()
                val content = noteContent.text.toString()

                if (!toBeEdited) {
                    viewModel.insertNote(
                            NoteReminder(title = title, content = content)
                    )
                } else {
                    viewModel.updateNote(
                            NoteReminder(id = id, title = title, content = content)
                    )
                }
                goBack()
            }
            else {
                Toast.makeText(this, "Title or note is empty", Toast.LENGTH_SHORT).show()
            }
        }

        closeBtn.setOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}