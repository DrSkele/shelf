package com.skele.core.timer

import kotlinx.coroutines.flow.StateFlow

/**
 * Controller for the timer.
 */
interface TimerController {
    val timerState: StateFlow<TimerState>

    /**
     * Gets the time when the timer was started for the first time.
     * @return The start time of the timer in milliseconds.
     */
    fun getStartTime(): Long

    /**
     * Initializes the timer to the given time.
     * Calling this on any state will reset the timer and be [TimerState.Idle].
     * @param time The total time of timer in milliseconds.
     */
    fun setTimer(time: Long)

    /**
     * Starts the timer.
     * Changes the state to [TimerState.Running].
     */
    fun start()

    /**
     * Pauses the timer.
     * Changes the state to [TimerState.Paused].
     */
    fun pause()

    /**
     * Resumes the timer.
     * Changes the state to [TimerState.Running].
     */
    fun resume()

    /**
     * Resets the timer.
     * Changes the state to [TimerState.Idle].
     */
    fun reset()
}