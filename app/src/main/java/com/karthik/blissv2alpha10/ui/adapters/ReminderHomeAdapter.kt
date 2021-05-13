package com.karthik.blissv2alpha10.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.ui.ReminderHomeLayout
import com.karthik.blissv2alpha10.ui.ReminderHomeLayoutDirections
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel

class ReminderHomeAdapter(private val context: Context, private val fragment: ReminderHomeLayout, private val viewModel :  HomeViewModel): RecyclerView.Adapter<ReminderHomeAdapter.ReminderViewHolder>() {

    private val allReminders: ArrayList<NoteReminder> = ArrayList()

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reminderCardText = itemView.findViewById<TextView>(R.id.note_reminder_card_text)
        val reminderCardContent = itemView.findViewById<TextView>(R.id.note_reminder_card_content)
        val reminderCard = itemView.findViewById<MaterialCardView>(R.id.noteReminderCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val root = ReminderViewHolder(LayoutInflater.from(context).inflate(R.layout.note_reminder_card, parent, false))

        root.reminderCard.setOnClickListener {
//            safe args
            val action = ReminderHomeLayoutDirections.actionReminderHomeLayoutToNoteReminderView(allReminders[root.adapterPosition])
            viewModel.setCurrent(10)
            NavHostFragment.findNavController(fragment).navigate(action)
        }

        return root
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.reminderCardText.text = allReminders[position].title
        holder.reminderCardContent.text = allReminders[position].content
    }

    override fun getItemCount(): Int {
        return allReminders.size
    }

    fun updateList(newList: List<NoteReminder>?) {
        if (newList != null) {
            allReminders.clear()
            allReminders.addAll(newList.reversed())
        }

        notifyDataSetChanged()
    }

}