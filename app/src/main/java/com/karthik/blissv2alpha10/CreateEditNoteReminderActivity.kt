package com.karthik.blissv2alpha10

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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

        voiceRecorder.setOnClickListener {
            stopRecordIcon.isClickable = true
            stopRecordIcon.isVisible = true
            voiceRecordText.text = "Recording..."
            Toast.makeText(this, "click detected", Toast.LENGTH_SHORT).show()

            stopRecordIcon.setOnClickListener {
                Toast.makeText(this, "stopped", Toast.LENGTH_SHORT).show()
                voiceRecordText.text = "Voice note added"

                stopRecordIcon.setOnClickListener {
                    stopRecordIcon.isClickable = false
                    stopRecordIcon.isVisible = false
                    Toast.makeText(this, "removed", Toast.LENGTH_SHORT).show()
                    voiceRecordText.text = "Click to add a voice note"
                }
            }
        }


        addImage.setOnClickListener {

            val REQUEST_IMAGE_GET = 1

            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
//            if (intent.resolveActivity(packageManager) != null) {
//                startActivityForResult(intent, REQUEST_IMAGE_GET)
//            }
        }



        addReminder.setOnClickListener {
            val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select reminder date")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build()
            val timePicker =
                    MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(12)
                            .setMinute(10)
                            .setTitleText("Select reminder time")
                            .build()

            datePicker.show(supportFragmentManager, "select date")

            datePicker.addOnPositiveButtonClickListener {
                timePicker.show(supportFragmentManager, "select time")
            }

            timePicker.addOnPositiveButtonClickListener {
                // call back code
                Log.e("timee", "${datePicker.selection}, ${timePicker.hour}:${timePicker.minute}")
            }

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