package com.skele.pomodoro.domain.timer

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