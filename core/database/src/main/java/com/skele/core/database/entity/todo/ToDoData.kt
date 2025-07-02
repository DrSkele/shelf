package com.skele.core.database.entity.todo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class ToDoData {
    @Serializable
    @SerialName("simple")
    data object Default : ToDoData()

    @Serializable
    @SerialName("timed")
    data class Timed(
        val setTime: Long,
        val elapsedTime: Long,
    ) : ToDoData()
}
