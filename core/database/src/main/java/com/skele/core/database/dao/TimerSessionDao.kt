package com.skele.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.skele.core.database.entity.TimerSessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerSessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(timerSession: TimerSessionEntity): Long

    @Update
    suspend fun update(timerSession: TimerSessionEntity)

    @Delete
    suspend fun delete(timerSession: TimerSessionEntity)

    @Query("SELECT * FROM timer_session")
    fun getAllTimerSessions(): Flow<List<TimerSessionEntity>>
}
