package com.skele.core.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.skele.core.database.entity.TimerSettingsEntity

object DatabaseInitializerCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        db.execSQL(
            """
                    INSERT INTO ${TimerSettingsEntity.tableName} (
                        ${TimerSettingsEntity::description.name},
                        ${TimerSettingsEntity::pomodoroTimeMs.name},
                        ${TimerSettingsEntity::shortBreakEnabled.name},
                        ${TimerSettingsEntity::shortBreakTimeMs.name},
                        ${TimerSettingsEntity::longBreakEnabled.name},
                        ${TimerSettingsEntity::longBreakTimeMs.name},
                        ${TimerSettingsEntity::longBreakInterval.name}
                    ) VALUES (
                        'Default',
                        ${25 * 60 * 1000L},
                        1,
                        ${5 * 60 * 1000L},
                        1,
                        ${15 * 60 * 1000L},
                        4
                    )
                """,
        )
    }
}
