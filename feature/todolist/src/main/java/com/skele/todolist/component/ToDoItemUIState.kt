package com.skele.todolist.component

sealed interface ToDoItemUIState {
    val description: String
    val progress: Float

    data class Check(
        override val description: String,
        override val progress: Float,
        val isChecked: Boolean,
    ) : ToDoItemUIState

    data class CountDown(
        override val description: String,
        override val progress: Float,
        val isRunning: Boolean,
        val remainingTimeMs: Long,
    ) : ToDoItemUIState
}
