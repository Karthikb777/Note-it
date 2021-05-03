package com.karthik.blissv2alpha10.ui.viewModels

import android.app.Application
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.karthik.blissv2alpha10.database.AppDatabase
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.repository.NoteReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NoteReminderRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = NoteReminderRepository(db.getNoteReminderDao())
    }

    val allNotes: LiveData<List<NoteReminder>> = repository.allNoteReminder.asLiveData()
    var gotNote = MutableLiveData<Array<NoteReminder>>()

    fun getNoteByTitle(title: String) = viewModelScope.launch {
        gotNote.value = repository.getNoteByTitle(title)
        }

    @WorkerThread
    fun insertNote(noteReminder: NoteReminder) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNoteReminder(noteReminder)
    }

    @WorkerThread
    fun updateNote(noteReminder: NoteReminder) = viewModelScope.launch {
        repository.updateNoteReminder(noteReminder)
    }

    @WorkerThread
    fun deleteNote(noteReminder: NoteReminder) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNoteReminder(noteReminder)
    }

//    TODO : add additional methods here
}


