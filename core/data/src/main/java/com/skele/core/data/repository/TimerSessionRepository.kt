package com.skele.core.data.repository

import com.skele.core.model.TimerSession
import kotlinx.coroutines.flow.Flow

interface TimerSessionRepository {
    fun getSessionsList(): Flow<List<TimerSession>>

    suspend fun insertSession(session: TimerSession): Long

    suspend fun updateSession(session: TimerSession): Unit

    suspend fun deleteSession(session: TimerSession): Unit
}
