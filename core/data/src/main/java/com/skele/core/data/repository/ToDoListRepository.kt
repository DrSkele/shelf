package com.skele.core.data.repository

import com.skele.core.model.todo.ToDoItem
import kotlinx.coroutines.flow.Flow

interface ToDoListRepository {
    fun getAllToDos(): Flow<List<ToDoItem>>

    suspend fun insertToDo(toDo: ToDoItem): Long

    suspend fun upsertToDo(toDo: ToDoItem)

    suspend fun updateToDo(toDo: ToDoItem)

    suspend fun deleteToDo(toDo: ToDoItem)
}
