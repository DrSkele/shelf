package com.skele.core.model

data class TimerSession(
    val id: Long,
    val description: String,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
    val type: TimerSessionType,
    val status: TimerSessionStatus,
)

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
