package com.karthik.blissv2alpha10.database.daos

import androidx.room.*
import com.karthik.blissv2alpha10.database.entities.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTodos() : Flow<List<Todo>>

    @Query("SELECT * FROM todo_table WHERE todo LIKE :title")
    suspend fun getTodoByTitle(title: String) : Array<Todo>

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

//    TODO : add additional queries here for searching etc
}