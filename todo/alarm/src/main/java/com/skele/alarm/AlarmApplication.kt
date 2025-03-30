package com.skele.alarm

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlarmApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Alarm"
        val description = "Alarm Notification Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("alarm_channel", name, importance).apply {
            this.description = description
        }
        val notificationManager: NotificationManager =
            this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}