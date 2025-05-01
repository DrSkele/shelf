package com.skele.core.data.repository

import com.skele.core.model.TimerSettings
import kotlinx.coroutines.flow.Flow

interface TimerSettingsRepository {
    fun getSettingsList(): Flow<List<TimerSettings>>

    fun getSettings(id: Long): Flow<TimerSettings?>

    suspend fun insertSettings(settings: TimerSettings): Long

    suspend fun updateSettings(settings: TimerSettings): Unit

    suspend fun deleteSettings(settings: TimerSettings): Unit
}
