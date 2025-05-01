package com.skele.core.data.repository

import com.skele.core.database.dao.TimerSessionDao
import com.skele.core.database.entity.TimerSessionEntity
import com.skele.core.database.entity.TimerSessionEntityStatus
import com.skele.core.database.entity.TimerSessionEntityType
import com.skele.core.model.TimerSession
import com.skele.core.model.TimerSessionStatus
import com.skele.core.model.TimerSessionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomTimerSessionRepository @Inject constructor(
    private val timerSessionDao: TimerSessionDao,
) : TimerSessionRepository {
    override fun getSessionsList(): Flow<List<TimerSession>> =
        timerSessionDao.getAllTimerSessions().map { list -> list.map { it.toModel() } }

    override suspend fun insertSession(session: TimerSession): Long = timerSessionDao.insert(session.toEntity())

    override suspend fun updateSession(session: TimerSession) = timerSessionDao.update(session.toEntity())

    override suspend fun deleteSession(session: TimerSession) = timerSessionDao.delete(session.toEntity())
}

internal fun TimerSessionEntity.toModel(): TimerSession =
    TimerSession(
        id = id,
        description = description,
        startTime = startTime,
        endTime = endTime,
        duration = duration,
        type = type.toModel(),
        status = status.toModel(),
    )

internal fun TimerSession.toEntity(): TimerSessionEntity =
    TimerSessionEntity(
        id = id,
        description = description,
        startTime = startTime,
        endTime = endTime,
        duration = duration,
        type = type.toEntity(),
        status = status.toEntity(),
    )

internal fun TimerSessionEntityType.toModel(): TimerSessionType =
    when (this) {
        TimerSessionEntityType.POMODORO -> TimerSessionType.POMODORO
        TimerSessionEntityType.SHORT_BREAK -> TimerSessionType.SHORT_BREAK
        TimerSessionEntityType.LONG_BREAK -> TimerSessionType.LONG_BREAK
    }

internal fun TimerSessionType.toEntity(): TimerSessionEntityType =
    when (this) {
        TimerSessionType.POMODORO -> TimerSessionEntityType.POMODORO
        TimerSessionType.SHORT_BREAK -> TimerSessionEntityType.SHORT_BREAK
        TimerSessionType.LONG_BREAK -> TimerSessionEntityType.LONG_BREAK
    }

internal fun TimerSessionEntityStatus.toModel(): TimerSessionStatus =
    when (this) {
        TimerSessionEntityStatus.COMPLETED -> TimerSessionStatus.COMPLETED
        TimerSessionEntityStatus.SKIPPED -> TimerSessionStatus.SKIPPED
        TimerSessionEntityStatus.RESET -> TimerSessionStatus.RESET
        TimerSessionEntityStatus.INTERRUPTED -> TimerSessionStatus.INTERRUPTED
    }

internal fun TimerSessionStatus.toEntity(): TimerSessionEntityStatus =
    when (this) {
        TimerSessionStatus.COMPLETED -> TimerSessionEntityStatus.COMPLETED
        TimerSessionStatus.SKIPPED -> TimerSessionEntityStatus.SKIPPED
        TimerSessionStatus.RESET -> TimerSessionEntityStatus.RESET
        TimerSessionStatus.INTERRUPTED -> TimerSessionEntityStatus.INTERRUPTED
    }
