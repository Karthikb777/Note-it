package com.karthik.blissv2alpha10.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.karthik.blissv2alpha10.R

class ReminderHomeAdapter(private val context: Context): RecyclerView.Adapter<ReminderHomeAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reminderCardText = itemView.findViewById<TextView>(R.id.note_reminder_card_text)
        val reminderCardContent = itemView.findViewById<TextView>(R.id.note_reminder_card_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val root = ReminderViewHolder(LayoutInflater.from(context).inflate(R.layout.note_reminder_card, parent, false))

        return root
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.reminderCardText.text = "hello world"
        holder.reminderCardContent.text = "hello world"
    }

    override fun getItemCount(): Int {
        return 50
    }

}