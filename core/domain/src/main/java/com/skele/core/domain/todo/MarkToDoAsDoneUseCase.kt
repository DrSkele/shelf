package com.skele.core.domain.todo

import com.skele.core.data.repository.ToDoListRepository
import com.skele.core.model.todo.ToDoItem
import javax.inject.Inject

class MarkToDoAsDoneUseCase @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
) {
    suspend operator fun invoke(toDo: ToDoItem) {
        toDoListRepository.updateToDo(
            when (toDo) {
                is ToDoItem.Check -> toDo.copy(isDone = true)
                is ToDoItem.CountDown -> toDo.copy(isDone = true)
            },
        )
    }
}
