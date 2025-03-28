package com.skele.alarm.ui.alarm

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

@Composable
fun AlarmPage(
    modifier: Modifier = Modifier,
    viewModel: AlarmViewModel,
) {
    val context = LocalContext.current
    val isRunning by viewModel.isRunning.collectAsState()
    val alarmFired by viewModel.alarmFired.collectAsState()

    LaunchedEffect(alarmFired) {
        if (alarmFired) {
            Toast.makeText(context, "Alarm Fired", Toast.LENGTH_LONG).show()
            viewModel.resetAlarmFired()
        }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.setAlarm(context)
        }) {
            Text(if (isRunning) "Alarm running" else "Set Alarm")
        }
    }
}