package com.skele.core.domain.todo

import com.skele.core.data.repository.ToDoListRepository
import javax.inject.Inject

class GetAllToDosUseCase @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
) {
    operator fun invoke() {
        toDoListRepository.getAllToDos()
    }
}
