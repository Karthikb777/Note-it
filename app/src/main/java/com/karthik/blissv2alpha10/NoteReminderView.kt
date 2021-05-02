package com.karthik.blissv2alpha10

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.karthik.blissv2alpha10.ui.NoteHomeLayoutDirections
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note_reminder_view.view.*

class NoteReminderView : Fragment() {

    private val args: NoteReminderViewArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        view.backBtn.setOnClickListener {
            goBack()
        }

        view.editBtn.setOnClickListener {
//            add edit functionality
        }

        view.deleteBtn.setOnClickListener {
            noteViewModel.deleteNote(args.viewNote)
            goBack()
        }

        return view
    }

    private fun goBack(){
        NavHostFragment.findNavController(this).navigate(R.id.action_noteReminderView_to_noteHomeLayout)
    }
}