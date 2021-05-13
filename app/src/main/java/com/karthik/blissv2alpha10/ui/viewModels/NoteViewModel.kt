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
import java.io.File

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : NoteReminderRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = NoteReminderRepository(db.getNoteReminderDao())
    }

    val allNotes: LiveData<List<NoteReminder>> = repository.allNoteReminder.asLiveData()
//    val allNotes: LiveData<List<NoteReminder>> = repository.allNoteReminder.asLiveData()
    var gotNote = MutableLiveData<Array<NoteReminder>>()

    fun getNoteByTitle(title: String) = viewModelScope.launch {
        gotNote.value = repository.getNoteByTitle(title)
        }

    fun searchNote(title: String) {
        gotNote.value = allNotes.value?.filter {
            it.title.contains(title)
        }?.toTypedArray()
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
    fun deleteNote(noteReminder: NoteReminder) {

//        cleanup code
        if (noteReminder.audioUri != ""){
            File(noteReminder.audioUri).delete()
        }

        if (noteReminder.imageUri != "") {
            File(noteReminder.imageUri).delete()
        }

        viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNoteReminder(noteReminder)
        }
    }

//    TODO : add additional methods here
}


