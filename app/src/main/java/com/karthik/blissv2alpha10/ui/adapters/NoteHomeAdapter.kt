package com.karthik.blissv2alpha10.ui.adapters

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.ui.NoteHomeLayout
import com.karthik.blissv2alpha10.ui.NoteHomeLayoutDirections
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel

class NoteHomeAdapter(private val context: Context, private val fragment: NoteHomeLayout, val viewModel: HomeViewModel): RecyclerView.Adapter<NoteHomeAdapter.NoteViewHolder>() {

    private val allNotes : ArrayList<NoteReminder> = ArrayList()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteCardText = itemView.findViewById<TextView>(R.id.note_reminder_card_text)
        val noteCardContent = itemView.findViewById<TextView>(R.id.note_reminder_card_content)
        val noteCard = itemView.findViewById<MaterialCardView>(R.id.noteCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val root = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_reminder_card, parent, false))

        root.noteCard.setOnClickListener {
//          safe args
            val action = NoteHomeLayoutDirections.actionNoteHomeLayoutToNoteReminderView(allNotes[root.adapterPosition])
            NavHostFragment.findNavController(fragment).navigate(action)
        }
        return root
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.noteCardText.text = allNotes[position].title
        holder.noteCardContent.text = allNotes[position].content
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<NoteReminder>) {
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }
}