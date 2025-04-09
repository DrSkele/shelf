package com.skele.pomodoro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.skele.pomodoro.ui.screen.PomodoroScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PomodoroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PomodoroScreen()
        }
    }
}
