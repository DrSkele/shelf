package com.skele.core.system

import com.skele.core.common.DispatchersProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class DefaultTimerController @Inject constructor(
    private val scope: CoroutineScope,
    private val dispatchers: DispatchersProvider,
) : TimerController {
    private val _timerState = MutableStateFlow<TimerState>(TimerState.Idle(TimerData()))

    override val timerState: StateFlow<TimerState>
        get() = _timerState.asStateFlow()

    private var startTime: Long = 0L

    private var timerJob: Job? = null

    override fun getStartTime(): Long = startTime

    override fun setTimer(time: Long) {
        timerJob?.cancel()

        _timerState.value = TimerState.Idle(TimerData(totalTime = time, remainingTime = time))
    }

    override fun start() {
        if (timerJob?.isActive == true || _timerState.value is TimerState.Finished) return

        if (startTime == 0L) startTime = System.currentTimeMillis()
        timerJob?.cancel()
        timerJob =
            scope.launch(dispatchers.default) {
                supervisorScope {
                    _timerState.value = TimerState.Running(_timerState.value.data)
                    while (_timerState.value is TimerState.Running && _timerState.value.remainingTime > 0) {
                        delay(if (_timerState.value.remainingTime > 100L) 100L else _timerState.value.remainingTime)
                        _timerState.update { state ->
                            val remainingTime = state.data.remainingTime - 100L

                            if (remainingTime <= 0) {
                                state.toState<TimerState.Finished>(0)
                            } else {
                                state.toState<TimerState.Running>(remainingTime)
                            }
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
        startTime = 0L
        timerJob?.cancel()
        _timerState.value =
            TimerState.Idle(
                _timerState.value.data.copy(
                    remainingTime = _timerState.value.data.totalTime,
                ),
            )
    }
}
