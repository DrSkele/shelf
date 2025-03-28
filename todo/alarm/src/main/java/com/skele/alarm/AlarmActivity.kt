package com.skele.alarm

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.skele.alarm.ui.alarm.AlarmPage
import com.skele.alarm.ui.alarm.AlarmViewModel
import com.skele.alarm.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "AlarmActivity"

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {
    private val viewModel: AlarmViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SCHEDULE_EXACT_ALARM), 0)
        }

        viewModel.handleIntent(intent)

        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                AlarmPage(viewModel = viewModel)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        Log.d(TAG, "onNewIntent: $intent")
        super.onNewIntent(intent)
        intent.let { viewModel.handleIntent(it) }
    }
}
