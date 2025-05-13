package com.skele.core.stopwatch

data class StopwatchData(
    val elapsedTime: Long = 0,
)

sealed interface StopwatchState {
    val data: StopwatchData

    /**
     * Initial state of the stopwatch.
     */
    data class Idle(
        override val data: StopwatchData,
    ) : StopwatchState

    /**
     * Stopwatch in progress.
     */
    data class Running(
        override val data: StopwatchData,
    ) : StopwatchState

    /**
     * Stopwatch is stopped.
     */
    data class Paused(
        override val data: StopwatchData,
    ) : StopwatchState
}

/**
 * Generic function to transform the current state to a different StopwatchState type.
 * Must specify the type of the [StopwatchState]
 * @param elapsedTime Optional new elapsed time value
 * @return a new instance of the specified StopwatchState type
 * @throws IllegalArgumentException if the specified type is not [StopwatchState]
 */
inline fun <reified T : StopwatchState> StopwatchState.toState(elapsedTime: Long? = null): T {
    val newData =
        if (elapsedTime != null) {
            this.data.copy(elapsedTime = elapsedTime)
        } else {
            this.data
        }

    return when (T::class) {
        StopwatchState.Idle::class -> StopwatchState.Idle(newData) as T
        StopwatchState.Running::class -> StopwatchState.Running(newData) as T
        StopwatchState.Paused::class -> StopwatchState.Paused(newData) as T
        else -> throw IllegalArgumentException("Unknown StopwatchState type requested")
    }
}
