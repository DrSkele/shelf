package com.skele.pomodoro.domain.timer

import kotlinx.coroutines.flow.StateFlow

/**
 * Controller for the timer.
 */
interface TimerController {
    val timerState: StateFlow<TimerState>

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

data class TimerData(
    val remainingTime: Long = 0,
    val totalTime: Long = 0,
)

sealed interface TimerState {
    val data: TimerData

    /**
     * Initial state of the timer.
     */
    data class Idle(
        override val data: TimerData,
    ) : TimerState

    /**
     * Timer in progress.
     */
    data class Running(
        override val data: TimerData,
    ) : TimerState

    /**
     * Timer is stopped.
     */
    data class Paused(
        override val data: TimerData,
    ) : TimerState

    /**
     * Timer is finished.
     */
    data class Finished(
        override val data: TimerData,
    ) : TimerState
}

val TimerState.remainingTime get() = data.remainingTime
val TimerState.totalTime get() = data.totalTime
