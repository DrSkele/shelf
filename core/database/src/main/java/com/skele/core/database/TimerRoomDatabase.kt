package com.skele.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skele.core.database.dao.TimerSessionDao
import com.skele.core.database.dao.TimerSettingsDao
import com.skele.core.database.entity.TimerSessionEntity
import com.skele.core.database.entity.TimerSettingsEntity

@Database(
    entities = [TimerSettingsEntity::class, TimerSessionEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class TimerRoomDatabase : RoomDatabase() {
    abstract fun timerSettingsDao(): TimerSettingsDao

    abstract fun timerSessionDao(): TimerSessionDao

    companion object {
        // Factory method with proper initialization
        fun createDatabase(context: Context): TimerRoomDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    TimerRoomDatabase::class.java,
                    "app_database",
                ).fallbackToDestructiveMigration()
                .addCallback(TimerDatabaseInitializerCallback)
                .build()
    }
}
