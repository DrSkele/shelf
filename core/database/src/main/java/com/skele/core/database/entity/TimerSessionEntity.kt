package com.skele.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TimerSessionEntity.tableName)
data class TimerSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val type: TimerSessionEntityType,
    val status: TimerSessionEntityStatus,
) {
    companion object {
        const val tableName = "timer_session"
    }
}

enum class TimerSessionEntityType {
    POMODORO,
    SHORT_BREAK,
    LONG_BREAK,
}

enum class TimerSessionEntityStatus {
    COMPLETED,
    SKIPPED,
    RESET,
    INTERRUPTED,
}
