package com.skele.pomodoro.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.skele.pomodoro.domain.TimerController
import com.skele.pomodoro.domain.remainingTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TimerForegroundService : Service() {
    private lateinit var timerController: TimerController

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        startForeground(NOTIFICATION_ID, buildNotification("Timer running"))

        serviceScope.launch {
            timerController.timerState.collect { state ->
                val content = formatTime(state.remainingTime)
                val updated = buildNotification("⏱ $content")
                notificationManager.notify(NOTIFICATION_ID, updated)
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun Context.buildNotification(contentText: String): Notification =
        NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentTitle("Pomodoro Timer")
            .setContentText(contentText)
//            .setSmallIcon(R.drawable.ic_timer)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .build()

    private fun formatTime(ms: Long): String {
        val totalSec = ms / 1000
        val minutes = totalSec / 60
        val seconds = totalSec % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    companion object {
        private const val CHANNEL_ID = "timer_channel"
        private const val CHANNEL_NAME = "Timer Notifications"
        private const val NOTIFICATION_ID = 1

        fun createNotificationChannel(context: Context) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW,
                ).apply {
                    description = "Notification for ongoing Pomodoro timer"
                }

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}