package com.skele.feature.timer

import com.skele.core.model.TimerSettings
import com.skele.core.timer.TimerState

sealed interface TimerUIState {
    data object Loading : TimerUIState

    data class Success(
        val settings: TimerSettings,
        val timerState: TimerState,
    ) : TimerUIState

    data class Error(
        val message: String,
    ) : TimerUIState
}
