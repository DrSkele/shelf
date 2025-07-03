package com.skele.core.domain.todo

import com.skele.core.data.repository.ToDoListRepository
import com.skele.core.model.todo.ToDoItem
import javax.inject.Inject

class AddToDoUseCase @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
) {
    suspend operator fun invoke(toDo: ToDoItem) {
        toDoListRepository.insertToDo(toDo)
    }
}
