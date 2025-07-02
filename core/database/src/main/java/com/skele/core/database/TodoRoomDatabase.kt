package com.skele.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skele.core.database.dao.ToDoDao
import com.skele.core.database.entity.todo.ToDoEntity

@Database(entities = [ToDoEntity::class], version = 1, exportSchema = true)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun todoDao(): ToDoDao

    companion object {
        fun createDatabase(context: Context): TodoRoomDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    TodoRoomDatabase::class.java,
                    "todo_database",
                ).fallbackToDestructiveMigration()
                .build()
    }
}
