package com.skele.todolist.component

sealed interface ToDoItemUIState {
    val description: String

    data class Check(
        override val description: String,
        val isChecked: Boolean,
    ) : ToDoItemUIState

    data class CountDown(
        override val description: String,
        val remainingTime: Long,
    ) : ToDoItemUIState
}
