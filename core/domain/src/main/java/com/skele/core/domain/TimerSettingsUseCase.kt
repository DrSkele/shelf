package com.skele.core.domain

import com.skele.core.data.repository.TimerSettingsRepository
import com.skele.core.model.TimerSettings
import com.skele.core.model.toEntity
import javax.inject.Inject

class TimerSettingsUseCase @Inject constructor(
    private val timerSettingsRepository: TimerSettingsRepository,
) {
    suspend fun getSettingsList() = timerSettingsRepository.getSettingsList()

    suspend fun getSettings(id: Long) = timerSettingsRepository.getSettings(id)

    suspend fun insertSettings(settings: TimerSettings) = timerSettingsRepository.insertSettings(settings.toEntity())

    suspend fun updateSettings(settings: TimerSettings) = timerSettingsRepository.updateSettings(settings.toEntity())

    suspend fun deleteSettings(settings: TimerSettings) = timerSettingsRepository.deleteSettings(settings.toEntity())
}
