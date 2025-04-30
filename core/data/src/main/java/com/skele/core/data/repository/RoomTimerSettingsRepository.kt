package com.skele.core.data.repository

import com.skele.core.database.dao.TimerSettingsDao
import com.skele.core.database.entity.TimerSettingsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomTimerSettingsRepository @Inject constructor(
    private val timerSettingsDao: TimerSettingsDao,
) : TimerSettingsRepository {
    override suspend fun getSettingsList(): Flow<List<TimerSettingsEntity>> = timerSettingsDao.getAllTimerSettingsFlow()

    override suspend fun getSettings(id: Long): Flow<TimerSettingsEntity?> = timerSettingsDao.getTimerSettingsByIdFlow(id)

    override suspend fun insertSettings(settings: TimerSettingsEntity): Long = timerSettingsDao.insert(settings)

    override suspend fun updateSettings(settings: TimerSettingsEntity) = timerSettingsDao.update(settings)

    override suspend fun deleteSettings(settings: TimerSettingsEntity) = timerSettingsDao.delete(settings)
}
