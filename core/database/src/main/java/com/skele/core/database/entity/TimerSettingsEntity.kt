package com.skele.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a timer settings entity in the database.
 *
 * @param id Unique id
 * @param description Description for the timer
 * @param pomodoroTimeMs Pomodoro time in milliseconds
 * @param shortBreakEnabled Whether short break is enabled. If disabled, there is no short break between pomodoros.
 * @param shortBreakTimeMs Short break time in milliseconds
 * @param longBreakEnabled Whether long break is enabled. Short break must be enabled prior to long break. If disabled, there is no long break replacing short break.
 * @param longBreakTimeMs Long break time in milliseconds
 * @param longBreakInterval Long break will take place of short break after this many pomodoros.
 */
@Entity(tableName = TimerSettingsEntity.tableName)
data class TimerSettingsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val description: String,
    val pomodoroTimeMs: Long,
    val shortBreakEnabled: Boolean,
    val shortBreakTimeMs: Long,
    val longBreakEnabled: Boolean,
    val longBreakTimeMs: Long,
    val longBreakInterval: Int,
) {
    companion object {
        const val tableName = "timer_settings"
    }
}
