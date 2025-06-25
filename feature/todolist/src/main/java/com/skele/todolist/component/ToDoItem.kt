package com.skele.todolist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skele.core.ui.icons.CheckBox_Blank
import com.skele.core.ui.icons.CheckBox_Filled
import com.skele.core.ui.icons.CustomIcons

@Composable
fun ToDoItem(
    modifier: Modifier = Modifier,
    uiState: ToDoItemUIState,
) {
    val buttonText =
        with(uiState) {
            when (this) {
                is ToDoItemUIState.Check -> if (isChecked) "Done" else "Ready"
                is ToDoItemUIState.CountDown -> if (remainingTimeMs > 0) remainingTimeMs else "Finished"
            }.toString()
        }

    val buttonIcon =
        with(uiState) {
            when (this) {
                is ToDoItemUIState.Check -> if (isChecked) CustomIcons.CheckBox_Filled else CustomIcons.CheckBox_Blank
                is ToDoItemUIState.CountDown -> if (isRunning) Icons.Default.PlayArrow else null
            }
        }

    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(elevation = 16.dp, shape = RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .clip(RoundedCornerShape(8.dp))
                .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier,
            text = uiState.description,
        )
        ProgressButton(
            modifier = Modifier,
            text = buttonText,
            icon = buttonIcon,
            progress = uiState.progress,
            backgroundColor = Color.Blue,
            overlayColor = Color.White,
        )
    }
}

@Preview
@Composable
fun ToDoItemPreview() {
    ToDoItem(
        uiState =
            ToDoItemUIState.Check(
                description = "Test",
                isChecked = true,
                progress = 0.5f,
            ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ToDoItemPreviewWithBackground() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        ToDoItem(
            uiState =
                ToDoItemUIState.Check(
                    description = "Test",
                    isChecked = true,
                    progress = 0.5f,
                ),
        )
    }
}
