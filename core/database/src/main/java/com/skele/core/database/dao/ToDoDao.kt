package com.skele.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.skele.core.database.entity.todo.ToDoEntity

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(toDo: ToDoEntity): Long

    @Update
    suspend fun update(toDo: ToDoEntity)

    @Upsert
    suspend fun upsert(toDo: ToDoEntity)

    @Delete
    suspend fun delete(toDo: ToDoEntity)

    @Query("SELECT * FROM todo")
    suspend fun getAllToDos(): List<ToDoEntity>
}