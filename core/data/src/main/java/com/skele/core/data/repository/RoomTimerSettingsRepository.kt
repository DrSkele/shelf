package com.skele.core.data.repository

import com.skele.core.database.dao.TimerSettingsDao
import com.skele.core.database.entity.TimerSettingsEntity
import com.skele.core.model.TimerSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomTimerSettingsRepository @Inject constructor(
    private val timerSettingsDao: TimerSettingsDao,
) : TimerSettingsRepository {
    override fun getSettingsList(): Flow<List<TimerSettings>> =
        timerSettingsDao.getAllTimerSettingsFlow().map { list -> list.map { it.toModel() } }

    override fun getSettings(id: Long): Flow<TimerSettings?> = timerSettingsDao.getTimerSettingsByIdFlow(id).map { it?.toModel() }

    override suspend fun insertSettings(settings: TimerSettings): Long = timerSettingsDao.insert(settings.toEntity())

    override suspend fun updateSettings(settings: TimerSettings) = timerSettingsDao.update(settings.toEntity())

    override suspend fun deleteSettings(settings: TimerSettings) = timerSettingsDao.delete(settings.toEntity())
}

internal fun TimerSettings.toEntity(): TimerSettingsEntity =
    TimerSettingsEntity(
        id = id,
        description = description,
        pomodoroTimeMs = pomodoroTimeMs,
        shortBreakEnabled = shortBreakEnabled,
        shortBreakTimeMs = shortBreakTimeMs,
        longBreakEnabled = longBreakEnabled,
        longBreakTimeMs = longBreakTimeMs,
        longBreakInterval = longBreakInterval,
    )

internal fun TimerSettingsEntity.toModel(): TimerSettings =
    TimerSettings(
        id = id,
        description = description,
        pomodoroTimeMs = pomodoroTimeMs,
        shortBreakEnabled = shortBreakEnabled,
        shortBreakTimeMs = shortBreakTimeMs,
        longBreakEnabled = longBreakEnabled,
        longBreakTimeMs = longBreakTimeMs,
        longBreakInterval = longBreakInterval,
    )
