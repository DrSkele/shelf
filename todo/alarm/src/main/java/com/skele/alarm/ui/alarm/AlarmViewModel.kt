package com.skele.alarm.ui.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skele.alarm.alarm.AlarmScheduler
import com.skele.alarm.alarm.AlarmStateStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AlarmEvent {
    object RequestExactAlarmPermission : AlarmEvent
    object ShowAlarmFinishedToast : AlarmEvent
}
@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    alarmStateStore: AlarmStateStore
) : ViewModel() {

    private val _isAlarmRunning = MutableStateFlow(false)
    val isAlarmRunning: StateFlow<Boolean> = _isAlarmRunning

    private val _eventFlow = MutableSharedFlow<AlarmEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val startTime: StateFlow<Long?> = alarmStateStore.startTimeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val endTime: StateFlow<Long?> = alarmStateStore.endTimeFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun startAlarm(durationMillis: Long) {
        val scheduled = alarmScheduler.scheduleAlarm(durationMillis)
        if (!scheduled) {
            viewModelScope.launch {
                _eventFlow.emit(AlarmEvent.RequestExactAlarmPermission)
            }
        } else {
            _isAlarmRunning.value = true
        }
    }

    fun onAlarmFired() {
        viewModelScope.launch {
            _isAlarmRunning.value = false
            alarmScheduler.cancelAlarm()
            _eventFlow.emit(AlarmEvent.ShowAlarmFinishedToast)
        }
    }
}