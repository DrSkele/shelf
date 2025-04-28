package com.skele.pomodoro.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing timer preferences and state.
 */
interface TimerRepository {
    /**
     * Get the configured pomodoro work session time in milliseconds.
     */
    fun getPomodoroTime(): Flow<Long>

    /**
     * Get the configured short break time in milliseconds.
     */
    fun getShortBreakTime(): Flow<Long>

    /**
     * Get the configured long break time in milliseconds.
     */
    fun getLongBreakTime(): Flow<Long>

    /**
     * Get the current pomodoro time as a one-time value.
     * @return The current pomodoro time in milliseconds.
     */
    suspend fun getPomodoroTimeOnce(): Long

    /**
     * Get the current short break time as a one-time value.
     * @return The current short break time in milliseconds.
     */
    suspend fun getShortBreakTimeOnce(): Long

    /**
     * Get the current long break time as a one-time value.
     * @return The current long break time in milliseconds.
     */
    suspend fun getLongBreakTimeOnce(): Long

    /**
     * Update the pomodoro work session time.
     * @param timeMs The time in milliseconds.
     */
    suspend fun updatePomodoroTime(timeMs: Long)

    /**
     * Update the short break time.
     * @param timeMs The time in milliseconds.
     */
    suspend fun updateShortBreakTime(timeMs: Long)

    /**
     * Update the long break time.
     * @param timeMs The time in milliseconds.
     */
    suspend fun updateLongBreakTime(timeMs: Long)
}
