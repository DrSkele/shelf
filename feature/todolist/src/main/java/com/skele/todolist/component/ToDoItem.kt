package com.skele.todolist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ToDoItem(
    modifier: Modifier = Modifier,
    uiState: ToDoItemUIState,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .shadow(elevation = 16.dp, shape = RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .clip(RoundedCornerShape(8.dp))
                .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(uiState.description)

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ToDoItemPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ToDoItem(
            uiState =
                ToDoItemUIState.Check(
                    description = "Test",
                    isChecked = true,
                ),
        )
    }
}
