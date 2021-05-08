package com.karthik.blissv2alpha10.database.daos

import androidx.room.*
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteReminderDao {

    @Query("SELECT * FROM note_reminder_table ORDER BY id ASC")
    fun getAllNoteReminders() : Flow<List<NoteReminder>>

    @Query("SELECT * FROM note_reminder_table WHERE reminder != '' ORDER BY id ASC")
    fun getAllNoteRemindersWithReminders() : Flow<List<NoteReminder>>

    @Query("SELECT * FROM note_reminder_table WHERE title LIKE :title")
    suspend fun getNoteByTitle(title: String) : Array<NoteReminder>

    @Insert
    suspend fun insertNote(noteReminder: NoteReminder)

    @Update
    suspend fun updateNote(noteReminder: NoteReminder)

    @Delete
    suspend fun deleteNote(noteReminder: NoteReminder)

//   TODO : add additional queries here for searching etc
}