package com.karthik.blissv2alpha10.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.ui.NoteHomeLayout
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel

class NoteHomeAdapter(private val context: Context, private val fragment: NoteHomeLayout): RecyclerView.Adapter<NoteHomeAdapter.NoteViewHolder>() {
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteCardText = itemView.findViewById<TextView>(R.id.note_reminder_card_text)
        val noteCardContent = itemView.findViewById<TextView>(R.id.note_reminder_card_content)
        val noteCard = itemView.findViewById<MaterialCardView>(R.id.noteCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val root = NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.note_reminder_card, parent, false))
        root.noteCard.setOnClickListener {
//            add safe args here while navigating
//            TODO: add a back functionality to the back button
            NavHostFragment.findNavController(fragment).navigate(R.id.action_noteHomeLayout_to_noteReminderView)
        }
        return root
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.noteCardText.text = "hello world"
        holder.noteCardContent.text = "It is a long established fact that a reader will be distracted by the readable content of a page\n" +
                "        when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less\n" +
                "        normal distribution of letters, as opposed to using Content here, content here ,\n" +
                "        making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum\n" +
                "        as their default model text, and a search for lorem ipsum will uncover many web sites still in their infancy.\n" +
                "        Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."
    }

    override fun getItemCount(): Int {
        return 50
    }
}