package com.skele.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skele.core.database.dao.TimerSessionDao
import com.skele.core.database.dao.TimerSettingsDao
import com.skele.core.database.entity.TimerSessionEntity
import com.skele.core.database.entity.TimerSettingsEntity

@Database(entities = [TimerSettingsEntity::class, TimerSessionEntity::class], version = 1, exportSchema = true)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun timerSettingsDao(): TimerSettingsDao

    abstract fun timerSessionDao(): TimerSessionDao

    companion object {
        // Factory method with proper initialization
        fun createDatabase(context: Context): AppRoomDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "app_database",
                ).fallbackToDestructiveMigration()
                .addCallback(DatabaseInitializerCallback)
                .build()
    }
}
