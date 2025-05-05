package com.skele.core.domain

import com.skele.core.common.DispatchersProvider
import com.skele.core.data.repository.TimerSessionRepository
import com.skele.core.model.TimerSession
import com.skele.core.model.TimerSessionStatus
import com.skele.core.model.TimerSessionType
import com.skele.core.model.TimerSettings
import com.skele.core.system.TimerController
import com.skele.core.system.TimerState
import com.skele.core.system.remainingTime
import com.skele.core.system.totalTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class TimerControllerUseCase @Inject constructor(
    private val scope: CoroutineScope,
    private val dispatcher: DispatchersProvider,
    private val timerSessionRepository: TimerSessionRepository,
    private val timerController: TimerController,
) {
    // Timer types
    enum class TimerType { POMODORO, SHORT_BREAK, LONG_BREAK }

    // Current state tracking
    private var currentTimerType = TimerType.POMODORO
    private var pomodoroCount = 0

    // Expose the timer state
    val timerState get() = timerController.timerState

    /**
     * Gets the timer state that automatically saves the session on completion([TimerState.Finished]).
     * @param settings Saves the session with given settings.
     */
    fun getTimerStateWithAutoSave(settings: TimerSettings): Flow<TimerState> =
        timerController.timerState.transform {
            if (it is TimerState.Finished) saveSession(it, settings, TimerSessionStatus.COMPLETED)
            emit(it)
        }

    // Save the current session
    suspend fun saveSession(
        state: TimerState,
        settings: TimerSettings,
        sessionStatus: TimerSessionStatus,
    ) {
        timerSessionRepository.insertSession(
            TimerSession(
                id = 0,
                groupId = settings.id,
                description = settings.description,
                startTime = timerController.getStartTime(),
                endTime = System.currentTimeMillis(),
                duration = state.totalTime - state.remainingTime,
                type =
                    when (currentTimerType) {
                        TimerType.POMODORO -> TimerSessionType.POMODORO
                        TimerType.SHORT_BREAK -> TimerSessionType.SHORT_BREAK
                        TimerType.LONG_BREAK -> TimerSessionType.LONG_BREAK
                    },
                status = sessionStatus,
            ),
        )
    }

    // Timer control methods
    fun setTimer(timeMs: Long) = timerController.setTimer(timeMs)

    fun start() = timerController.start()

    fun pause() = timerController.pause()

    fun resume() = timerController.resume()

    fun reset() = timerController.reset()

    // Pomodoro cycle management
    fun proceedToNextTime(settings: TimerSettings) {
        when (currentTimerType) {
            TimerType.POMODORO -> {
                // After a pomodoro, increment count
                pomodoroCount++

                if (!settings.shortBreakEnabled) {
                    // If short break is not enabled, go to pomodoro
                    currentTimerType = TimerType.POMODORO
                    setTimer(settings.pomodoroTimeMs)
                } else if (settings.longBreakEnabled && pomodoroCount % settings.longBreakInterval == 0) {
                    // Check if long break is enabled & interval is met
                    currentTimerType = TimerType.LONG_BREAK
                    setTimer(settings.longBreakTimeMs)
                } else {
                    // Otherwise, go to short break
                    currentTimerType = TimerType.SHORT_BREAK
                    setTimer(settings.shortBreakTimeMs)
                }
            }

            TimerType.SHORT_BREAK, TimerType.LONG_BREAK -> {
                // After any break, go back to pomodoro
                currentTimerType = TimerType.POMODORO
                setTimer(settings.pomodoroTimeMs)
            }
        }
    }

    // Get current state info
    fun getCurrentTimerType(): TimerType = currentTimerType

    fun getCompletedPomodoroCount(): Int = pomodoroCount

    // Reset all state (for starting fresh)
    fun resetAll() {
        pomodoroCount = 0
        currentTimerType = TimerType.POMODORO
        timerController.reset()
    }
}
