package com.karthik.blissv2alpha10

import android.app.Application
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.activity_create_edit_note_reminder.view.*
import kotlinx.android.synthetic.main.fragment_note_reminder_view.*
import kotlinx.android.synthetic.main.fragment_note_reminder_view.view.*

const val ID = "com.karthik.notesApp.ID"
const val TITLE = "com.karthik.notesApp.TITLE"
const val IMG_URI = "com.karthik.notesApp.IMG_URI"
const val AUD_URI = "com.karthik.notesApp.AUD_URI"
const val REMINDER = "com.karthik.notesApp.REMINDER"

class NoteReminderView : Fragment() {
    private val args: NoteReminderViewArgs by navArgs()
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setCurrent(-1)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val noteViewModel : NoteViewModel by activityViewModels()
//        val noteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(
//                Application()
//        )).get(NoteViewModel::class.java)

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

        if (args.viewNote.audioUri != "") {
//            val myUri = "/storage/emulated/0/Bliss/audio/test.mp3"
            playVoiceNote(view)
        } else {
            view.voice_note_card.isVisible = false
        }


        if (view.note_reminder_content.urls.isNotEmpty()) {
            view.linkLayout.isVisible = true
            view.linkLayout.isClickable = true
            view.urls.text = view.note_reminder_content.urls[0]?.url

            view.linkLayout.setOnClickListener {
                CustomTabsIntent.Builder().build()
                        .launchUrl(requireContext(), Uri.parse(view.note_reminder_content.urls[0].url));
            }

        } else {
            view.linkLayout.isVisible = false
            view.linkLayout.isClickable = false
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
                putExtra(IMG_URI, args.viewNote.imageUri)
                putExtra(AUD_URI, args.viewNote.audioUri)
                putExtra(REMINDER, args.viewNote.reminder)
            }
            startActivity(editIntent)
        }

        view.deleteBtn.setOnClickListener {
            noteViewModel.deleteNote(args.viewNote)
            goBack()
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun playVoiceNote(view: View) {
        val mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                    AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            )
//            "/storage/emulated/0/Bliss/audio/test.mp3"
            setDataSource(args.viewNote.audioUri)
            try {
                prepare()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        view.voice_note_card.setOnClickListener {
            when(mediaPlayer.isPlaying) {
                true -> {
                    mediaPlayer.pause()
//                    it.playPause.setImageResource(R.drawable.ic_play)
                    view.voiceNoteText.text = "Play voice note"

                }
                false -> {
                    mediaPlayer.start()
//                    it.playPause.setImageResource(R.drawable.ic_pause)
                    view.voiceNoteText.text = "Pause voice note"

                }
            }
            mediaPlayer.setOnCompletionListener {
//                view.playPause.setImageResource(R.drawable.ic_play)
                view.voiceNoteText.text = "Play voice note"
            }
//            voiceNoteSeekbar.max = mediaPlayer.duration

//            val handler = Handler()
//            val runnable: Runnable = object : Runnable {
//                override fun run() {
////                current.setText(Integer.toString(mediaPlayer.getCurrentPosition()/1000));
//                    voiceNoteSeekbar.progress = mediaPlayer.currentPosition
//                    handler.postDelayed(this, 1)
//                }
//            }
//            handler.post(runnable)

//            voiceNoteSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                    mediaPlayer.seekTo(progress)
//                }
//
//                override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                }
//
//                override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                }
//
//            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeViewModel.setCurrent(0)
    }

    private fun goBack(){
//        activity?.onBackPressed()
        NavHostFragment.findNavController(this).navigate(R.id.action_noteReminderView_to_noteHomeLayout)
        homeViewModel.setCurrent(0)
        homeViewModel.setSearch(0)
    }

}