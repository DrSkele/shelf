package com.skele.alarm.ui.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skele.alarm.AlarmActivity
import com.skele.alarm.receiver.AlarmBroadcastReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
sealed interface AlarmEvent {
    object RequestExactAlarmPermission : AlarmEvent
    object ShowAlarmFinishedToast : AlarmEvent
}

@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmManager: AlarmManager
) : ViewModel() {

    private val _isAlarmRunning = MutableStateFlow(false)
    val isAlarmRunning: StateFlow<Boolean> = _isAlarmRunning

    private val _eventFlow = MutableSharedFlow<AlarmEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    @SuppressLint("MissingPermission")
    fun startAlarm(context: Context, durationMillis: Long = 1 * 60 * 1000) {
        if (!canScheduleExactAlarms(context)) {
            viewModelScope.launch { _eventFlow.emit(AlarmEvent.RequestExactAlarmPermission) }
            return
        }

        val alarmIntent = Intent(context, AlarmBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context, 0, alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val showIntent = Intent(context, AlarmActivity::class.java)
        val showPendingIntent = PendingIntent.getActivity(
            context, 0, showIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = System.currentTimeMillis() + durationMillis
        val alarmClockInfo = AlarmManager.AlarmClockInfo(triggerTime, showPendingIntent)

        alarmManager.setAlarmClock(alarmClockInfo, alarmPendingIntent)
        _isAlarmRunning.value = true
    }

    fun handleIntent(intent: Intent) {
        if (intent.getBooleanExtra("alarm_fired", false)) {
            _isAlarmRunning.value = false
            viewModelScope.launch { _eventFlow.emit(AlarmEvent.ShowAlarmFinishedToast) }
        }
    }

    private fun canScheduleExactAlarms(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }
}
