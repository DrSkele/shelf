package com.skele.alarm.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.skele.alarm.MainActivity
import com.skele.alarm.receiver.AlarmReceiver
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmScheduler @Inject constructor(
    private val alarmManager: AlarmManager,
    private val alarmStateStore: AlarmStateStore,
    @ApplicationContext private val context: Context
) {
    fun scheduleAlarm(durationMillis: Long): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()
        ) return false

        val now = System.currentTimeMillis()
        val endTime = now + durationMillis

        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context, 0, alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val showIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val showPendingIntent = PendingIntent.getActivity(
            context, 0, showIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val info = AlarmManager.AlarmClockInfo(endTime, showPendingIntent)
        alarmManager.setAlarmClock(info, alarmPendingIntent)

        CoroutineScope(Dispatchers.IO).launch {
            alarmStateStore.saveStartTime(now)
        }
        return true
    }

    fun cancelAlarm() {
        val alarmIntent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(
            context, 0, alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(alarmPendingIntent)
        CoroutineScope(Dispatchers.IO).launch {
            alarmStateStore.saveStartTime(System.currentTimeMillis())
        }
    }
}
