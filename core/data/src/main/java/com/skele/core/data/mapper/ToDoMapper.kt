package com.skele.core.data.mapper

import com.skele.core.database.entity.todo.ToDoData
import com.skele.core.database.entity.todo.ToDoEntity
import com.skele.core.model.todo.ToDoItem

fun ToDoEntity.toModel(): ToDoItem =
    when (val data = this.data) {
        is ToDoData.Default ->
            ToDoItem.Check(
                id = id,
                description = description,
                isDone = isDone,
                schedule = schedule,
                timeStamp = timeStamp,
            )

        is ToDoData.Timed ->
            ToDoItem.CountDown(
                id = id,
                description = description,
                isDone = isDone,
                schedule = schedule,
                timeStamp = timeStamp,
                setTime = data.setTime,
                elapsedTime = data.elapsedTime,
            )
    }

fun ToDoItem.toEntity(): ToDoEntity =
    when (this) {
        is ToDoItem.Check ->
            ToDoEntity(
                id = id,
                description = description,
                isDone = isDone,
                schedule = schedule,
                timeStamp = timeStamp,
                data = ToDoData.Default,
            )

        is ToDoItem.CountDown ->
            ToDoEntity(
                id = id,
                description = description,
                isDone = isDone,
                schedule = schedule,
                timeStamp = timeStamp,
                data =
                    ToDoData.Timed(
                        setTime = setTime,
                        elapsedTime = elapsedTime,
                    ),
            )
    }
