package com.skele.pomodoro.data.timer

import com.skele.pomodoro.domain.timer.TimerController
import com.skele.pomodoro.domain.timer.TimerData
import com.skele.pomodoro.domain.timer.TimerState
import com.skele.pomodoro.domain.timer.remainingTime
import com.skele.pomodoro.util.DispatchersProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultTimerController @Inject constructor(
    private val scope: CoroutineScope,
    private val dispatchersProvider: DispatchersProvider,
) : TimerController {
    private val _timerState = MutableStateFlow<TimerState>(TimerState.Idle(TimerData()))

    override val timerState: StateFlow<TimerState>
        get() = _timerState.asStateFlow()

    private var timerJob: Job? = null

    override fun setTimer(time: Long) {
        timerJob?.cancel()

        _timerState.value = TimerState.Idle(TimerData(totalTime = time, remainingTime = time))
    }

    override fun start() {
        if (timerJob?.isActive == true || _timerState.value is TimerState.Finished) return

        timerJob?.cancel()
        timerJob =
            scope.launch(dispatchersProvider.default) {
                _timerState.value = TimerState.Running(_timerState.value.data)

                while (_timerState.value is TimerState.Running && _timerState.value.remainingTime > 0) {
                    delay(100L)
                    _timerState.update {
                        val remainingTime = (it as TimerState.Running).data.remainingTime - 100L

                        if (remainingTime <= 0) {
                            TimerState.Finished(it.data.copy(remainingTime = 0))
                        } else {
                            TimerState.Running(it.data.copy(remainingTime = remainingTime))
                        }
                    }
                }
            }
    }

    override fun pause() {
        timerJob?.cancel()
        _timerState.value = TimerState.Paused(_timerState.value.data)
    }

    override fun resume() {
        if (_timerState.value is TimerState.Paused) {
            start()
        }
    }

    override fun reset() {
        timerJob?.cancel()
        _timerState.value =
            TimerState.Idle(
                _timerState.value.data.copy(
                    remainingTime = _timerState.value.data.totalTime,
                ),
            )
    }
}
