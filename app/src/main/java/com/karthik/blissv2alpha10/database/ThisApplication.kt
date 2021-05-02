package com.karthik.blissv2alpha10.database

import android.app.Application
import com.karthik.blissv2alpha10.repository.NoteReminderRepository
import com.karthik.blissv2alpha10.repository.TodoRepository

class ThisApplication : Application() {
    /*
    Using by lazy so the database and the repositories are only created when they're needed
    rather than when the application starts
    */
    val database by lazy {
        AppDatabase.getDatabase(this)
    }

    val noteRepository by lazy {
        NoteReminderRepository(database.getNoteReminderDao())
    }

    val todoRepository by lazy {
        TodoRepository(database.getTodoDao())
    }
}