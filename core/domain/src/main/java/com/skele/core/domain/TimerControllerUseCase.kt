package com.skele.core.domain

import com.skele.core.model.TimerSettings
import com.skele.core.system.TimerController
import javax.inject.Inject

class TimerControllerUseCase @Inject constructor(
    private val timerController: TimerController,
) {
    // Timer types
    enum class TimerType { POMODORO, SHORT_BREAK, LONG_BREAK }

    // Current state tracking
    private var currentTimerType = TimerType.POMODORO
    private var pomodoroCount = 0

    // Expose the timer state
    val timerState get() = timerController.timerState

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
