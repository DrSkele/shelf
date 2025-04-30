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
    val type: TimerSessionType,
    val status: TimerSessionStatus,
) {
    companion object {
        const val tableName = "timer_session"
    }
}

enum class TimerSessionType {
    POMODORO,
    SHORT_BREAK,
    LONG_BREAK,
}

enum class TimerSessionStatus {
    COMPLETED,
    SKIPPED,
    RESET,
    INTERRUPTED,
}
