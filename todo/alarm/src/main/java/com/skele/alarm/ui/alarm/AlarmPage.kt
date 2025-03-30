package com.skele.alarm.ui.alarm

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.DateFormat
import java.util.Date

@Composable
fun AlarmPage(viewModel: AlarmViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val isRunning by viewModel.isAlarmRunning.collectAsState()
    val startTime by viewModel.startTime.collectAsState()
    val endTime by viewModel.endTime.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AlarmEvent.RequestExactAlarmPermission -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        context.startActivity(intent)
                    } else {
                        Toast.makeText(
                            context,
                            "Exact alarm permission not required on this version",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is AlarmEvent.ShowAlarmFinishedToast -> {
                    Toast.makeText(
                        context,
                        "Alarm Finished! Time for a break.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.startAlarm(durationMillis = 1 * 60 * 1000)
        }) {
            Text(if (isRunning) "Alarm Running" else "Start Alarm")
        }

        startTime?.let {
            Text("Start Time: ${DateFormat.getTimeInstance().format(Date(it))}")
        }
        endTime?.let {
            Text("End Time: ${DateFormat.getTimeInstance().format(Date(it))}")
        }
    }
}