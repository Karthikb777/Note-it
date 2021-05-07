package com.karthik.blissv2alpha10

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.activity_create_edit_note_reminder.*
import java.io.File
import java.io.IOException
import java.net.URI
import java.nio.file.FileSystem
import java.time.Instant
import java.time.ZoneId
import java.util.*

class CreateEditNoteReminderActivity : AppCompatActivity() {

    private val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE: Int = 0
    private val RECORD_AUDIO_PERMISSION_REQUEST_CODE: Int = 1
    private val IMAGE_GET_REQUEST_CODE: Int = 2

    private lateinit var audTitle: String
    private lateinit var imgTitle: String
    private lateinit var reminderTime: String

    private lateinit var noteToBeEdited: NoteReminder

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_note_reminder)

        val id = intent.getIntExtra("com.karthik.notesApp.ID", 0)
        val title = intent.getStringExtra("com.karthik.notesApp.TITLE")

        reminderTime = ""
        audTitle = ""
        imgTitle = ""

        var toBeEdited = false

        val viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(Application())).get(NoteViewModel::class.java)

        if (id != 0 && title!!.isNotEmpty()) {
            viewModel.getNoteByTitle(title)
            viewModel.gotNote.observe(this, Observer {
                noteTitle.setText(it[0].title)
                noteContent.setText(it[0].content)
                if(it[0].audioUri != ""){
                    voiceRecordText.setText("Click X to delete")

                    stopRecordIcon.isClickable = true
                    stopRecordIcon.isVisible = true

                    val delURI = it[0].audioUri

                    stopRecordIcon.setOnClickListener {
                        stopRecordIcon.isClickable = false
                        stopRecordIcon.isVisible = false

                        File(delURI).delete()
                        audTitle = ""
                        Toast.makeText(this, "Voice note removed", Toast.LENGTH_SHORT).show()
                        voiceRecordText.text = "Click to add a voice note"
                    }
                }
                noteToBeEdited = it[0]
                toBeEdited = true
            })
        }

        voiceRecorder.setOnClickListener {
            if (isAudioPermissionGranted() && isWriteExternalStoragePermissionGranted()) {
                stopRecordIcon.isClickable = true
                stopRecordIcon.isVisible = true
                voiceRecordText.text = "Recording..."

                val URI = getAppDirectories("aud")
                val currentTime = Calendar.getInstance().time

                val recorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    setOutputFile("${URI}/${currentTime}.mp3")
                    setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                    try {
                        prepare()
                    } catch (e: IOException) {
                        Log.e("io", "prepare() failed")
                    }
                    start()
                }

                stopRecordIcon.setOnClickListener {
                    Toast.makeText(this, "stopped", Toast.LENGTH_SHORT).show()
                    voiceRecordText.text = "Voice note added"
//                    setting the audTitle to the audio uri to be saved to db later
                    audTitle = "$URI/$currentTime.mp3"
                    recorder.apply {
                        stop()
                        release()
                    }

                    stopRecordIcon.setOnClickListener {
                        stopRecordIcon.isClickable = false
                        stopRecordIcon.isVisible = false

                        File("$URI/$currentTime.mp3").delete()
                        audTitle = ""
                        Toast.makeText(this, "Voice note removed", Toast.LENGTH_SHORT).show()
                        voiceRecordText.text = "Click to add a voice note"
                    }
                }
            } else {
                requestRecordAudioPermission()
                requestWritePermission()
            }
        }

        addImage.setOnClickListener {

            if (isWriteExternalStoragePermissionGranted() && Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            Log.e("writePerm", "write permission granted")
                val URI = getAppDirectories("img")

                val currentTime = Calendar.getInstance().time

                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivityForResult(intent, IMAGE_GET_REQUEST_CODE)
                }

            } else {
            requestWritePermission()
            }
        }

        addReminder.setOnClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

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
                reminderTime = "${datePicker.selection},${timePicker.hour},${timePicker.minute}"
                Log.e("timee", "${
                    Instant.ofEpochMilli(datePicker.selection!!).atZone(ZoneId.systemDefault()).toLocalDate()
                },${timePicker.hour},${timePicker.minute}")
                reminderTime = "${
                    Instant.ofEpochMilli(datePicker.selection!!).atZone(ZoneId.systemDefault()).toLocalDate()
                },${timePicker.hour},${timePicker.minute}"
            }
        }

        saveBtn.setOnClickListener {
            if (noteTitle.text.isNotEmpty() && noteContent.text.isNotEmpty()) {
                val title = noteTitle.text.toString()
                val content = noteContent.text.toString()

                if (!toBeEdited) {
                    viewModel.insertNote(
                            NoteReminder(title = title, content = content, audioUri = audTitle, imageUri = imgTitle, reminder = reminderTime)
                    )
                } else {
                    viewModel.updateNote(
                            NoteReminder(id = id, title = title, content = content, audioUri = audTitle, imageUri = imgTitle, reminder = reminderTime)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_GET_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
            val fullPhotoUri: Uri = data!!.data!!

            Log.e("uriPic", "${fullPhotoUri.lastPathSegment}")
            // Do work with photo saved at fullPhotoUri

            val fullPhoto: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, fullPhotoUri)

            val URI = getAppDirectories("img")
            val currentTime = Calendar.getInstance().time
            val destination = "$URI/$currentTime.jpg"

            File(destination).outputStream().use { out ->
                fullPhoto.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
            }
            imgTitle = destination
        }
    }

    private fun getAppDirectories(type: String) : String {
        val imgURI: String
        val audURI: String
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            Log.e("writePerm1", "" +
                    "${Environment.getStorageDirectory()}")

            val rootDir = Environment.getStorageDirectory()

            audURI = if (File(rootDir.toString(), "Bliss/audio").exists()) {
                File(rootDir.toString(), "Bliss/audio").toString()
            } else {
                File(rootDir.toString(), "Bliss/audio").mkdirs()
                File(rootDir.toString(), "Bliss/audio").toString()
            }

            imgURI = if (File(rootDir.toString(), "Bliss/images").exists()) {
                File(rootDir.toString(), "Bliss/images").toString()
            } else {
                File(rootDir.toString(), "Bliss/images").mkdirs()
                File(rootDir.toString(), "Bliss/images").toString()
            }

        }  else {
            Log.e("writePerm2", "${Environment.getExternalStorageDirectory()}")

            val rootDir = Environment.getExternalStorageDirectory()

            audURI = if (File(rootDir.toString(), "Bliss/audio").exists()) {
                File(rootDir.toString(), "Bliss/audio").toString()
            } else {
                File(rootDir.toString(), "Bliss/audio").mkdirs()
                File(rootDir.toString(), "Bliss/audio").toString()
            }

            imgURI = if (File(rootDir.toString(), "Bliss/images").exists()) {
                File(rootDir.toString(), "Bliss/images").toString()
            } else {
                File(rootDir.toString(), "Bliss/images").mkdirs()
                File(rootDir.toString(), "Bliss/images").toString()
            }
        }
        when(type) {
            "img" -> return imgURI
            "aud" -> return audURI
        }
        return ""
    }

    private fun isAudioPermissionGranted() =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO).equals(PackageManager.PERMISSION_GRANTED)


    private fun isWriteExternalStoragePermissionGranted() =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE).equals(PackageManager.PERMISSION_GRANTED)
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE).equals(PackageManager.PERMISSION_GRANTED)


    private fun requestWritePermission() {
        val permissions = mutableListOf<String>()
            if (!isWriteExternalStoragePermissionGranted()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                permissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            } else {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE)
        }
    }

    private fun requestRecordAudioPermission() {
        val permissions = mutableListOf<String>()
        if (!isAudioPermissionGranted()) {
            permissions.add(Manifest.permission.RECORD_AUDIO)
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), RECORD_AUDIO_PERMISSION_REQUEST_CODE)
        }
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}