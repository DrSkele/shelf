package com.skele.core.data.repository

import com.skele.core.database.entity.TimerSettingsEntity
import kotlinx.coroutines.flow.Flow

interface TimerSettingsRepository {
    suspend fun getSettingsList(): Flow<List<TimerSettingsEntity>>

    suspend fun getSettings(id: Long): Flow<TimerSettingsEntity?>

    suspend fun insertSettings(settings: TimerSettingsEntity): Long

    suspend fun updateSettings(settings: TimerSettingsEntity): Unit

    suspend fun deleteSettings(settings: TimerSettingsEntity): Unit
}
