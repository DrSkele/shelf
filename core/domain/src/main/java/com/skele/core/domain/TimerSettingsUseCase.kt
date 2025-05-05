package com.skele.core.domain

import com.skele.core.data.repository.TimerSettingsRepository
import com.skele.core.model.TimerSettings
import javax.inject.Inject

class TimerSettingsUseCase @Inject constructor(
    private val timerSettingsRepository: TimerSettingsRepository,
) {
    fun getSettingsList() = timerSettingsRepository.getSettingsList()

    fun getSettings(id: Long) = timerSettingsRepository.getSettings(id)

    suspend fun insertSettings(settings: TimerSettings) = timerSettingsRepository.insertSettings(settings)

    suspend fun updateSettings(settings: TimerSettings) = timerSettingsRepository.updateSettings(settings)

    suspend fun deleteSettings(settings: TimerSettings) = timerSettingsRepository.deleteSettings(settings)
}
