package com.skele.pomodoro.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreTimerRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) : TimerRepository {
    private val Context.dataStore by preferencesDataStore(name = "timer_preferences")

    private object PreferencesKeys {
        val POMODORO_TIME = longPreferencesKey("pomodoro_time")
        val SHORT_BREAK_TIME = longPreferencesKey("short_break_time")
        val LONG_BREAK_TIME = longPreferencesKey("long_break_time")
    }

    companion object {
        const val DEFAULT_POMODORO_TIME = 25 * 60 * 1000L // 25 minutes
        const val DEFAULT_SHORT_BREAK_TIME = 5 * 60 * 1000L // 5 minutes
        const val DEFAULT_LONG_BREAK_TIME = 15 * 60 * 1000L // 15 minutes
    }

    // Extension function to handle DataStore exceptions
    private fun <T> Flow<Preferences>.getWithDefault(
        key: Preferences.Key<T>,
        defaultValue: T,
    ): Flow<T> =
        this
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[key] ?: defaultValue
            }

    override fun getPomodoroTime(): Flow<Long> =
        context.dataStore.data
            .getWithDefault(PreferencesKeys.POMODORO_TIME, DEFAULT_POMODORO_TIME)

    override fun getShortBreakTime(): Flow<Long> =
        context.dataStore.data
            .getWithDefault(PreferencesKeys.SHORT_BREAK_TIME, DEFAULT_SHORT_BREAK_TIME)

    override fun getLongBreakTime(): Flow<Long> =
        context.dataStore.data
            .getWithDefault(PreferencesKeys.LONG_BREAK_TIME, DEFAULT_LONG_BREAK_TIME)

    override suspend fun getPomodoroTimeOnce(): Long = getPomodoroTime().first()

    override suspend fun getShortBreakTimeOnce(): Long = getShortBreakTime().first()

    override suspend fun getLongBreakTimeOnce(): Long = getLongBreakTime().first()

    override suspend fun updatePomodoroTime(timeMs: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.POMODORO_TIME] = timeMs
        }
    }

    override suspend fun updateShortBreakTime(timeMs: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHORT_BREAK_TIME] = timeMs
        }
    }

    override suspend fun updateLongBreakTime(timeMs: Long) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LONG_BREAK_TIME] = timeMs
        }
    }
}
