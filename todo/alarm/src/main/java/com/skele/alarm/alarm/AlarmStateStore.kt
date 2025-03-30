package com.skele.alarm.alarm

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmStateStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "alarm_prefs")
    private val startKey = longPreferencesKey("start_time")
    private val endKey = longPreferencesKey("end_time")

    val startTimeFlow: Flow<Long?> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[startKey] }

    val endTimeFlow: Flow<Long?> = context.dataStore.data
        .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
        .map { prefs -> prefs[endKey] }

    suspend fun saveStartTime(value: Long) {
        context.dataStore.edit { it[startKey] = value }
    }

    suspend fun saveEndTime(value: Long) {
        context.dataStore.edit { it[endKey] = value }
    }
}
