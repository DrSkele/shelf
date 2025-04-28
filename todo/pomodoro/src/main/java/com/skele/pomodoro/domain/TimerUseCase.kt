package com.skele.pomodoro.domain

import com.skele.pomodoro.data.TimerRepository
import javax.inject.Inject

class TimerUseCase @Inject constructor(
    private val timerRepository: TimerRepository,
    private val timerController: TimerController,
) {
    // Timer types
    enum class TimerType { POMODORO, SHORT_BREAK, LONG_BREAK }

    // Current state tracking
    private var currentTimerType = TimerType.POMODORO
    private var pomodoroCount = 0
    private val pomodorosBeforeLongBreak = 4

    // Expose the timer state
    val timerState get() = timerController.timerState

    // Timer repository access methods - both Flow and one-time versions
    fun getPomodoroTime() = timerRepository.getPomodoroTime()

    fun getShortBreakTime() = timerRepository.getShortBreakTime()

    fun getLongBreakTime() = timerRepository.getLongBreakTime()

    suspend fun updatePomodoroTime(timeMs: Long) = timerRepository.updatePomodoroTime(timeMs)

    suspend fun updateShortBreakTime(timeMs: Long) = timerRepository.updateShortBreakTime(timeMs)

    suspend fun updateLongBreakTime(timeMs: Long) = timerRepository.updateLongBreakTime(timeMs)

    // Timer configuration methods
    suspend fun setToPomodoro() {
        currentTimerType = TimerType.POMODORO
        val pomodoroTime = timerRepository.getPomodoroTimeOnce()
        timerController.setTimer(pomodoroTime)
    }

    suspend fun setToShortBreak() {
        currentTimerType = TimerType.SHORT_BREAK
        val shortBreakTime = timerRepository.getShortBreakTimeOnce()
        timerController.setTimer(shortBreakTime)
    }

    suspend fun setToLongBreak() {
        currentTimerType = TimerType.LONG_BREAK
        val longBreakTime = timerRepository.getLongBreakTimeOnce()
        timerController.setTimer(longBreakTime)
    }

    // Timer control methods
    fun setTimer(timeMs: Long) = timerController.setTimer(timeMs)

    fun start() = timerController.start()

    fun pause() = timerController.pause()

    fun resume() = timerController.resume()

    fun reset() = timerController.reset()

    // Pomodoro cycle management
    suspend fun proceedToNextTime() {
        when (currentTimerType) {
            TimerType.POMODORO -> {
                // After a pomodoro, increment count
                pomodoroCount++

                // Check if we need a long break
                if (pomodoroCount % pomodorosBeforeLongBreak == 0) {
                    setToLongBreak()
                } else {
                    setToShortBreak()
                }
            }

            TimerType.SHORT_BREAK, TimerType.LONG_BREAK -> {
                // After any break, go back to pomodoro
                setToPomodoro()
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
