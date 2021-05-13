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

    var gotReminder = MutableLiveData<Array<NoteReminder>>()

    fun getNoteByTitle(title: String) = viewModelScope.launch {
        gotReminder.value = repository.getNoteByTitle(title)
    }

    fun searchReminder(title: String) {
        gotReminder.value = allReminders.value?.filter {
            it.title.contains(title)
        }?.toTypedArray()
    }

    val allReminders: LiveData<List<NoteReminder>> = repository.allNoteReminderWithReminder.asLiveData()

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
