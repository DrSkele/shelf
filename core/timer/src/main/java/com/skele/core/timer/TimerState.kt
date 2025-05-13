package com.skele.core.timer


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

/**
 * Generic function to transform the current state to a different TimerState type.
 * Must specify the type of the [TimerState]
 * @param remainingTime Optional new remaining time value
 * @return a new instance of the specified TimerState type
 * @throws IllegalArgumentException if the specified type is not [TimerState]
 */
inline fun <reified T : TimerState> TimerState.toState(remainingTime: Long? = null): T {
    val newData =
        if (remainingTime != null) {
            this.data.copy(remainingTime = remainingTime)
        } else {
            this.data
        }

    return when (T::class) {
        TimerState.Idle::class -> TimerState.Idle(newData) as T
        TimerState.Running::class -> TimerState.Running(newData) as T
        TimerState.Paused::class -> TimerState.Paused(newData) as T
        TimerState.Finished::class -> TimerState.Finished(newData) as T
        else -> throw IllegalArgumentException("Unknown TimerState type requested")
    }
}

inline val TimerState.remainingTime get() = data.remainingTime
inline val TimerState.totalTime get() = data.totalTime
