package com.skele.core.domain.todo

import com.skele.core.data.repository.ToDoListRepository
import com.skele.core.model.todo.ToDoItem
import javax.inject.Inject

class DeleteToDoUseCase @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
) {
    suspend operator fun invoke(toDo: ToDoItem) {
        toDoListRepository.deleteToDo(toDo)
    }
}
