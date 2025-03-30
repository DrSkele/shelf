package com.skele.alarm

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.skele.alarm.ui.alarm.AlarmPage
import com.skele.alarm.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "AlarmActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SCHEDULE_EXACT_ALARM), 0)
        }

        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                AlarmPage()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        Log.d(TAG, "onNewIntent: $intent")
        super.onNewIntent(intent)
        intent.let { viewModel.setEndTimeToNow() }
    }
}
