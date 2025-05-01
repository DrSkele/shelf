package com.skele.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.skele.core.database.entity.TimerSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerSettingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timerSettings: TimerSettingsEntity): Long

    @Update
    suspend fun update(timerSettings: TimerSettingsEntity)

    @Delete
    suspend fun delete(timerSettings: TimerSettingsEntity)

    @Query("SELECT * FROM timer_settings")
    suspend fun getAllTimerSettings(): List<TimerSettingsEntity>

    @Query("SELECT * FROM timer_settings")
    fun getAllTimerSettingsFlow(): Flow<List<TimerSettingsEntity>>

    @Query("SELECT * FROM timer_settings WHERE id = :id")
    fun getTimerSettingsByIdFlow(id: Long): Flow<TimerSettingsEntity?>

    @Query("SELECT COUNT(*) FROM timer_settings")
    suspend fun getTimerSettingsCount(): Int
}
