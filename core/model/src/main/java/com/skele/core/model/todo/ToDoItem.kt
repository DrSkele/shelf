package com.skele.core.model.todo

import com.skele.core.model.common.Schedule

sealed interface ToDoItem {
    val id: Long
    val description: String
    val isDone: Boolean
    val schedule: Schedule
    val timeStamp: Long

    data class Check(
        override val id: Long,
        override val description: String,
        override val isDone: Boolean,
        override val schedule: Schedule,
        override val timeStamp: Long,
    ) : ToDoItem

    data class CountDown(
        override val id: Long,
        override val description: String,
        override val isDone: Boolean,
        override val schedule: Schedule,
        override val timeStamp: Long,
        val setTime: Long,
        val elapsedTime: Long,
    ) : ToDoItem
}
