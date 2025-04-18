package com.skele.pomodoro

import android.app.Application
import com.skele.pomodoro.service.timer.TimerForegroundService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        TimerForegroundService.createNotificationChannel(this)
    }
}
