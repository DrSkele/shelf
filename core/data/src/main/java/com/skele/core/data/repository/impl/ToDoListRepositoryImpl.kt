package com.skele.core.data.repository.impl

import com.skele.core.common.DispatchersProvider
import com.skele.core.data.mapper.toEntity
import com.skele.core.data.mapper.toModel
import com.skele.core.data.repository.ToDoListRepository
import com.skele.core.database.dao.ToDoDao
import com.skele.core.model.todo.ToDoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToDoListRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao,
    private val dispatchersProvider: DispatchersProvider,
) : ToDoListRepository {
    override fun getAllToDos(): Flow<List<ToDoItem>> =
        toDoDao
            .getAllToDos()
            .map { list -> list.map { entity -> entity.toModel() } }
            .flowOn(dispatchersProvider.IO)

    override suspend fun insertToDo(toDo: ToDoItem): Long =
        withContext(dispatchersProvider.IO) {
            toDoDao.insert(toDo.toEntity())
        }

    override suspend fun upsertToDo(toDo: ToDoItem) =
        withContext(dispatchersProvider.IO) {
            toDoDao.upsert(toDo.toEntity())
        }

    override suspend fun updateToDo(toDo: ToDoItem) =
        withContext(dispatchersProvider.IO) {
            toDoDao.update(toDo.toEntity())
        }

    override suspend fun deleteToDo(toDo: ToDoItem) =
        withContext(dispatchersProvider.IO) {
            toDoDao.delete(toDo.toEntity())
        }
}
