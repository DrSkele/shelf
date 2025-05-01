package com.skele.core.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skele.core.database.BaseDatabaseTest
import com.skele.core.database.entity.TimerSettingsEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimerSettingsDaoTest : BaseDatabaseTest() {
    private lateinit var timerSettingsDao: TimerSettingsDao

    override fun setupDatabase() {
        super.setupDatabase()
        timerSettingsDao = database.timerSettingsDao()
    }

    @Test
    fun timerSettingsDao_inserts_and_verifies_timer_settings() =
        runTest {
            // Create a timer settings entity
            val timerSettings =
                TimerSettingsEntity(
                    id = 0, // Room will auto-generate
                    description = "Test Timer",
                    pomodoroTimeMs = 25 * 60 * 1000L,
                    shortBreakEnabled = true,
                    shortBreakTimeMs = 5 * 60 * 1000L,
                    longBreakEnabled = true,
                    longBreakTimeMs = 15 * 60 * 1000L,
                    longBreakInterval = 4,
                )

            // Insert the timer settings
            val id = timerSettingsDao.insert(timerSettings)

            // Get all timer settings from database
            val allSettings = timerSettingsDao.getAllTimerSettings()

            // Verify the timer settings was inserted
            assertEquals(1, allSettings.size)
            assertEquals(id, allSettings[0].id)
            assertEquals(timerSettings.description, allSettings[0].description)
            assertEquals(timerSettings.pomodoroTimeMs, allSettings[0].pomodoroTimeMs)
        }

    @Test
    fun timerSettingsDao_updates_and_verifies_changes() =
        runTest {
            // Insert a timer settings
            val timerSettings =
                TimerSettingsEntity(
                    id = 0,
                    description = "Test Timer",
                    pomodoroTimeMs = 25 * 60 * 1000L,
                    shortBreakEnabled = true,
                    shortBreakTimeMs = 5 * 60 * 1000L,
                    longBreakEnabled = true,
                    longBreakTimeMs = 15 * 60 * 1000L,
                    longBreakInterval = 4,
                )

            val id = timerSettingsDao.insert(timerSettings)

            // Create an updated timer settings
            val updatedSettings =
                timerSettings.copy(
                    id = id,
                    description = "Updated Timer",
                    pomodoroTimeMs = 30 * 60 * 1000L,
                )

            // Update the timer settings
            timerSettingsDao.update(updatedSettings)

            // Get all timer settings
            val allSettings = timerSettingsDao.getAllTimerSettings()

            // Verify the timer settings was updated
            assertEquals(1, allSettings.size)
            assertEquals("Updated Timer", allSettings[0].description)
            assertEquals(30 * 60 * 1000L, allSettings[0].pomodoroTimeMs)
        }

    @Test
    fun timerSettingsDao_deletes_and_verifies_removal() =
        runTest {
            // Insert a timer settings
            val timerSettings =
                TimerSettingsEntity(
                    id = 0,
                    description = "Test Timer",
                    pomodoroTimeMs = 25 * 60 * 1000L,
                    shortBreakEnabled = true,
                    shortBreakTimeMs = 5 * 60 * 1000L,
                    longBreakEnabled = true,
                    longBreakTimeMs = 15 * 60 * 1000L,
                    longBreakInterval = 4,
                )

            val id = timerSettingsDao.insert(timerSettings)
            val insertedSettings = timerSettings.copy(id = id)

            // Delete the timer settings
            timerSettingsDao.delete(insertedSettings)

            // Get all timer settings
            val allSettings = timerSettingsDao.getAllTimerSettings()

            // Verify the timer settings was deleted
            assertEquals(0, allSettings.size)
        }

    @Test
    fun timerSettingsDao_flow_operations_emit_expected_data() =
        runTest {
            // Insert a timer settings
            val timerSettings =
                TimerSettingsEntity(
                    id = 0,
                    description = "Test Timer",
                    pomodoroTimeMs = 25 * 60 * 1000L,
                    shortBreakEnabled = true,
                    shortBreakTimeMs = 5 * 60 * 1000L,
                    longBreakEnabled = true,
                    longBreakTimeMs = 15 * 60 * 1000L,
                    longBreakInterval = 4,
                )

            val id = timerSettingsDao.insert(timerSettings)

            // Test getAllTimerSettingsFlow
            val allSettingsFlow = timerSettingsDao.getAllTimerSettingsFlow().first()
            assertEquals(1, allSettingsFlow.size)

            // Test getTimerSettingsByIdFlow
            val settingsById = timerSettingsDao.getTimerSettingsByIdFlow(id).first()
            assertEquals(id, settingsById?.id)

            // Test with invalid ID
            val invalidSettings = timerSettingsDao.getTimerSettingsByIdFlow(-1).first()
            assertNull(invalidSettings)
        }

    @Test
    fun timerSettingsDao_count_returns_correct_number() =
        runTest {
            // Initially there should be no timer settings
            assertEquals(0, timerSettingsDao.getTimerSettingsCount())

            // Insert a timer settings
            val timerSettings =
                TimerSettingsEntity(
                    id = 0,
                    description = "Test Timer",
                    pomodoroTimeMs = 25 * 60 * 1000L,
                    shortBreakEnabled = true,
                    shortBreakTimeMs = 5 * 60 * 1000L,
                    longBreakEnabled = true,
                    longBreakTimeMs = 15 * 60 * 1000L,
                    longBreakInterval = 4,
                )

            timerSettingsDao.insert(timerSettings)

            // Now there should be one timer settings
            assertEquals(1, timerSettingsDao.getTimerSettingsCount())
        }
}
