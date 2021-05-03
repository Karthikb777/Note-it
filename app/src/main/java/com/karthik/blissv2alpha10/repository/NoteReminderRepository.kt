package com.karthik.blissv2alpha10.repository

import androidx.lifecycle.LiveData
import com.karthik.blissv2alpha10.database.daos.NoteReminderDao
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import kotlinx.coroutines.flow.Flow

class NoteReminderRepository(private val noteReminderDao: NoteReminderDao) {

    val allNoteReminder : Flow<List<NoteReminder>> = noteReminderDao.getAllNoteReminders()

    suspend fun getNoteByTitle(title: String) : Array<NoteReminder> {
        return noteReminderDao.getNoteByTitle(title)
    }

    suspend fun insertNoteReminder(noteReminder: NoteReminder) {
        noteReminderDao.insertNote(noteReminder)
    }

    suspend fun updateNoteReminder(noteReminder: NoteReminder) {
        noteReminderDao.updateNote(noteReminder)
    }

    suspend fun deleteNoteReminder(noteReminder: NoteReminder) {
        noteReminderDao.deleteNote(noteReminder)
    }

}