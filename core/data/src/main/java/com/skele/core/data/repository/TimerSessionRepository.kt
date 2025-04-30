package com.skele.core.data.repository

import com.skele.core.database.entity.TimerSessionEntity
import kotlinx.coroutines.flow.Flow

interface TimerSessionRepository {

    suspend fun getSessionsList(): Flow<List<TimerSessionEntity>>
}