package com.skele.feature.timer

import androidx.lifecycle.ViewModel
import com.skele.core.domain.TimerControllerUseCase
import com.skele.core.domain.TimerSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val timerControllerUseCase: TimerControllerUseCase,
    private val timerSettingsUseCase: TimerSettingsUseCase,
) : ViewModel() {

}