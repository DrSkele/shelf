package com.skele.core.model.todo

sealed class ToDoItem(
    open val id: Long,
    open val description: String,
) {
    data class Check(
        override val id: Long,
        override val description: String,
        val isChecked: Boolean,
    ) : ToDoItem(id, description)

    data class CountDown(
        override val id: Long,
        override val description: String,
        val remainingTime: Long,
    ) : ToDoItem(id, description)
}
