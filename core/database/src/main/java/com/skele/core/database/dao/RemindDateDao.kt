package com.skele.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.skele.core.database.entity.reminder.RemindDateEntity

@Dao
interface RemindDateDao {
    @Insert
    suspend fun insert(remindDate: RemindDateEntity)

    @Query("SELECT * FROM remind_date")
    suspend fun getAll(): List<RemindDateEntity>

    @Query("SELECT * FROM remind_date WHERE reminderId = :reminderId")
    suspend fun getRemindDatesForReminder(reminderId: Long): List<RemindDateEntity>

    @Update
    suspend fun update(remindDate: RemindDateEntity)

    @Delete
    suspend fun delete(remindDate: RemindDateEntity)

    @Query("DELETE FROM remind_date WHERE id = :id")
    suspend fun deleteRemindDateById(id: Long): RemindDateEntity
}
