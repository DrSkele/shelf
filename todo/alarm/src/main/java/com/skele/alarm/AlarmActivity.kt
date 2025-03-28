package com.skele.alarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
