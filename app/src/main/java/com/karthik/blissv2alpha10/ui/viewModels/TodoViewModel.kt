package com.karthik.blissv2alpha10.ui.viewModels

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.*
import com.karthik.blissv2alpha10.database.AppDatabase
import com.karthik.blissv2alpha10.database.entities.Todo
import com.karthik.blissv2alpha10.repository.NoteReminderRepository
import com.karthik.blissv2alpha10.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : TodoRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = TodoRepository(db.getTodoDao())
    }

    val allTodos : LiveData<List<Todo>> = repository.alltodos.asLiveData()

    @WorkerThread
    fun insertTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTodo(todo)
    }

    @WorkerThread
    fun updateTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTodo(todo)
    }

    @WorkerThread
    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTodo(todo)
    }
}
