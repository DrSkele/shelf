package com.skele.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skele.core.database.converter.DateTimeConverter
import com.skele.core.database.dao.RemindDateDao
import com.skele.core.database.dao.ReminderDao
import com.skele.core.database.entity.reminder.RemindDateEntity
import com.skele.core.database.entity.reminder.ReminderEntity

@Database(
    entities = [ReminderEntity::class, RemindDateEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(DateTimeConverter::class)
abstract class ReminderRoomDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao

    abstract fun remindDateDao(): RemindDateDao

    companion object {
        fun create(context: Context): ReminderRoomDatabase =
            Room
                .databaseBuilder(
                    context.applicationContext,
                    ReminderRoomDatabase::class.java,
                    "reminder_database",
                ).fallbackToDestructiveMigration()
                .build()
    }
}
