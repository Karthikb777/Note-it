package com.karthik.blissv2alpha10.ui.viewModels

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.karthik.blissv2alpha10.database.AppDatabase
import com.karthik.blissv2alpha10.database.entities.NoteReminder
import com.karthik.blissv2alpha10.repository.NoteReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NoteReminderRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = NoteReminderRepository(db.getNoteReminderDao())
    }

    val allReminders: LiveData<List<NoteReminder>> = repository.allNoteReminder.asLiveData()

    @WorkerThread
    fun insertReminder(noteReminder: NoteReminder) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNoteReminder(noteReminder)
    }

    @WorkerThread
    fun updateReminder(noteReminder: NoteReminder) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNoteReminder(noteReminder)
    }

    @WorkerThread
    fun deleteReminder(noteReminder: NoteReminder) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNoteReminder(noteReminder)
    }
//    TODO : add additional methods here
}
