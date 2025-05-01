package com.skele.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skele.core.database.dao.TimerSettingsDao
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseInitializationTest {
    private lateinit var database: AppRoomDatabase
    private lateinit var timerSettingsDao: TimerSettingsDao

    @Before
    fun setupDatabaseWithCallback() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Here we're using the actual callback unlike our base test
        database =
            Room
                .inMemoryDatabaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                ).allowMainThreadQueries()
                .addCallback(DatabaseInitializerCallback)
                .build()

        timerSettingsDao = database.timerSettingsDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun database_initializes_with_default_timer_settings() =
        runTest {
            // Since we're using the real callback, we should have default settings
            val settings = timerSettingsDao.getAllTimerSettings()

            // Verify we have default settings
            assertEquals(1, settings.size)

            val defaultSettings = settings.first()
            assertEquals("Default", defaultSettings.description)
            assertEquals(25 * 60 * 1000L, defaultSettings.pomodoroTimeMs)
            assertEquals(true, defaultSettings.shortBreakEnabled)
            assertEquals(5 * 60 * 1000L, defaultSettings.shortBreakTimeMs)
            assertEquals(true, defaultSettings.longBreakEnabled)
            assertEquals(15 * 60 * 1000L, defaultSettings.longBreakTimeMs)
            assertEquals(4, defaultSettings.longBreakInterval)
        }

    @Test
    fun database_factory_method_creates_valid_instance() {
        // Test that the factory method works
        val context = ApplicationProvider.getApplicationContext<Context>()
        val factoryDatabase = AppRoomDatabase.createDatabase(context)

        // Just check that it doesn't crash and returns a database
        assertTrue(factoryDatabase is AppRoomDatabase)

        // Clean up
        factoryDatabase.close()
    }
}
