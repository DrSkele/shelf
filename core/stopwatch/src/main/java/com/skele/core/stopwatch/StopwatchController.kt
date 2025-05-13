package com.skele.core.stopwatch

import kotlinx.coroutines.flow.StateFlow

/**
 * Controller for the stopwatch
 */
interface StopwatchController {
    val stopwatchState: StateFlow<StopwatchState>

    /**
     * Starts the stopwatch.
     * Changes the state to [StopwatchState.Running].
     */
    fun start()

    /**
     * Pauses the stopwatch.
     * Changes the state to [StopwatchState.Paused].
     */
    fun pause()

    /**
     * Resumes the timer.
     * Changes the state to [StopwatchState.Running].
     */
    fun resume()

    /**
     * Resets the stopwatch.
     * Changes the state to [StopwatchState.Idle].
     */
    fun reset()

    /**
     * Cleans up any running coroutines in the controller.
     */
    fun cleanup()
}