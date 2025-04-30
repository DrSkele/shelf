package com.skele.core.model

import com.skele.core.database.TimerSettingsEntity

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

fun TimerSettings.toEntity(): com.skele.core.database.TimerSettingsEntity =
    com.skele.core.database.TimerSettingsEntity(
        id = id,
        description = description,
        pomodoroTimeMs = pomodoroTimeMs,
        shortBreakEnabled = shortBreakEnabled,
        shortBreakTimeMs = shortBreakTimeMs,
        longBreakEnabled = longBreakEnabled,
        longBreakTimeMs = longBreakTimeMs,
        longBreakInterval = longBreakInterval,
    )

fun com.skele.core.database.TimerSettingsEntity.toModel(): TimerSettings =
    TimerSettings(
        id = id,
        description = description,
        pomodoroTimeMs = pomodoroTimeMs,
        shortBreakEnabled = shortBreakEnabled,
        shortBreakTimeMs = shortBreakTimeMs,
        longBreakEnabled = longBreakEnabled,
        longBreakTimeMs = longBreakTimeMs,
        longBreakInterval = longBreakInterval,
    )