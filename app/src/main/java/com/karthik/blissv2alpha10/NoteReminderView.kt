package com.karthik.blissv2alpha10

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.karthik.blissv2alpha10.ui.NoteHomeLayoutDirections
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note_reminder_view.view.*
import java.util.*

const val ID = "com.karthik.notesApp.ID"
const val TITLE = "com.karthik.notesApp.TITLE"

class NoteReminderView : Fragment() {

    private val args: NoteReminderViewArgs by navArgs()
    private val homeViewModel : HomeViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeViewModel.setCurrent(-1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val noteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(
            Application()
        )).get(NoteViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_note_reminder_view, container, false)
        view.note_reminder_title.text = args.viewNote.title
        view.note_reminder_content.text = args.viewNote.content
        if (args.viewNote.reminder != "") {
            val reminder = args.viewNote.reminder.split(",")
            val date = "${reminder[0]}, ${reminder[1]}:${reminder[2]}"
            view.reminderTime.text = date
        } else {
            view.reminder.isVisible = false
        }
        if(args.viewNote.imageUri != "") {
            val noteImg = view.findViewById<ImageView>(R.id.noteImage)
            Glide.with(this).load(args.viewNote.imageUri).into(noteImg)
        }

        view.backBtn.setOnClickListener {
            goBack()
        }

        view.editBtn.setOnClickListener {
//            add edit functionality
            val editIntent = Intent(activity, CreateEditNoteReminderActivity::class.java).apply {
                putExtra(ID, args.viewNote.id)
                putExtra(TITLE, args.viewNote.title)
            }
            startActivity(editIntent)
        }

        view.deleteBtn.setOnClickListener {
            noteViewModel.deleteNote(args.viewNote)
            goBack()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.setCurrent(0)

    }

    private fun goBack(){
        NavHostFragment.findNavController(this).navigate(R.id.action_noteReminderView_to_noteHomeLayout)
        homeViewModel.setCurrent(0)
    }
}