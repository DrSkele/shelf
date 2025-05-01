package com.skele.core.model

class TimerSettings(
    val id: Long,
    val description: String,
    val pomodoroTimeMs: Long,
    val shortBreakEnabled: Boolean,
    val shortBreakTimeMs: Long,
    val longBreakEnabled: Boolean,
    val longBreakTimeMs: Long,
    val longBreakInterval: Int,
)
