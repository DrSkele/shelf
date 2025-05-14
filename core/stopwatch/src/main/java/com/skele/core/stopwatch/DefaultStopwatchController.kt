package com.skele.core.stopwatch

import com.skele.core.common.DispatchersProvider
import com.skele.core.common.TimeProvider
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

class DefaultStopwatchController @Inject constructor(
    private val scope: CoroutineScope,
    private val dispatchers: DispatchersProvider,
    private val time: TimeProvider
) : StopwatchController {
    private val _stopwatchState =
        MutableStateFlow<StopwatchState>(StopwatchState.Idle(StopwatchData()))
    override val stopwatchState: StateFlow<StopwatchState>
        get() = _stopwatchState.asStateFlow()

    private var stopwatchJob: Job? = null

    override fun start() {
        if (stopwatchJob?.isActive == true) return

        var timeMillis = time.currentTimeMillis()
        stopwatchJob =
            scope.launch(dispatchers.default) {
                supervisorScope {
                    _stopwatchState.value = StopwatchState.Running(StopwatchData(0))
                    while (_stopwatchState.value is StopwatchState.Running) {
                        delay(100)
                        val now = time.currentTimeMillis()
                        val delta = now - timeMillis
                        timeMillis = now
                        _stopwatchState.update { state ->
                            val elapsedTime = state.data.elapsedTime + delta
                            state.toState<StopwatchState.Running>(elapsedTime)
                        }
                    }
                }
            }
    }

    override fun pause() {
        stopwatchJob?.cancel()
        _stopwatchState.value = StopwatchState.Paused(_stopwatchState.value.data)
    }

    override fun resume() {
        if (_stopwatchState.value is StopwatchState.Paused) {
            start()
        }
    }

    override fun reset() {
        stopwatchJob?.cancel()
        _stopwatchState.value = StopwatchState.Idle(StopwatchData(0))
    }

    override fun cleanup() {
        stopwatchJob?.cancel()
    }
}
