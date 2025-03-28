package com.skele.alarm.ui.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.skele.alarm.AlarmActivity
import com.skele.alarm.receiver.AlarmBroadcastReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class AlarmViewModel @Inject constructor(
    private val alarmManager: AlarmManager
) : ViewModel() {

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _alarmFired = MutableStateFlow(false)
    val alarmFired: StateFlow<Boolean> = _alarmFired

    @SuppressLint("MissingPermission")
    fun setAlarm(context: Context, durationMillis: Long = 1 * 60 * 1000) {
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
        _isRunning.value = true
    }

    fun handleIntent(intent: Intent) {
        if (intent.getBooleanExtra("alarm_fired", false)) {
            _alarmFired.value = true
            _isRunning.value = false
        }
    }

    fun resetAlarmFired() {
        _alarmFired.value = false
    }
}