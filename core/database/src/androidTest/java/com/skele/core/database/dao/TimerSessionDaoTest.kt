package com.skele.core.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skele.core.database.BaseDatabaseTest
import com.skele.core.database.entity.TimerSessionEntity
import com.skele.core.database.entity.TimerSessionStatus
import com.skele.core.database.entity.TimerSessionType
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimerSessionDaoTest : BaseDatabaseTest() {
    private lateinit var timerSessionDao: TimerSessionDao

    override fun setupDatabase() {
        super.setupDatabase()
        timerSessionDao = database.timerSessionDao()
    }

    @Test
    fun timerSessionDao_inserts_and_verifies_session() =
        runTest {
            // Create a timer session entity
            val timerSession =
                TimerSessionEntity(
                    id = 0, // Room will auto-generate
                    description = "Pomodoro 1",
                    startTime = System.currentTimeMillis(),
                    endTime = System.currentTimeMillis() + 25 * 60 * 1000L,
                    duration = 25 * 60 * 1000L,
                    type = TimerSessionType.POMODORO,
                    status = TimerSessionStatus.COMPLETED,
                )

            // Insert the timer session
            val id = timerSessionDao.insert(timerSession)

            // Get all timer sessions from database
            val allSessions = timerSessionDao.getAllTimerSessions()

            // Verify the timer session was inserted
            assertEquals(1, allSessions.size)
            assertEquals(id, allSessions[0].id)
            assertEquals(timerSession.description, allSessions[0].description)
            assertEquals(timerSession.type, allSessions[0].type)
            assertEquals(timerSession.status, allSessions[0].status)
        }

    @Test
    fun timerSessionDao_updates_and_verifies_changes() =
        runTest {
            // Insert a timer session
            val timerSession =
                TimerSessionEntity(
                    id = 0,
                    description = "Pomodoro 1",
                    startTime = System.currentTimeMillis(),
                    endTime = System.currentTimeMillis() + 25 * 60 * 1000L,
                    duration = 25 * 60 * 1000L,
                    type = TimerSessionType.POMODORO,
                    status = TimerSessionStatus.COMPLETED,
                )

            val id = timerSessionDao.insert(timerSession)

            // Create an updated timer session
            val updatedSession =
                timerSession.copy(
                    id = id,
                    description = "Updated Pomodoro",
                    status = TimerSessionStatus.INTERRUPTED,
                )

            // Update the timer session
            timerSessionDao.update(updatedSession)

            // Get all timer sessions
            val allSessions = timerSessionDao.getAllTimerSessions()

            // Verify the timer session was updated
            assertEquals(1, allSessions.size)
            assertEquals("Updated Pomodoro", allSessions[0].description)
            assertEquals(TimerSessionStatus.INTERRUPTED, allSessions[0].status)
        }

    @Test
    fun timerSessionDao_deletes_and_verifies_removal() =
        runTest {
            // Insert a timer session
            val timerSession =
                TimerSessionEntity(
                    id = 0,
                    description = "Pomodoro 1",
                    startTime = System.currentTimeMillis(),
                    endTime = System.currentTimeMillis() + 25 * 60 * 1000L,
                    duration = 25 * 60 * 1000L,
                    type = TimerSessionType.POMODORO,
                    status = TimerSessionStatus.COMPLETED,
                )

            val id = timerSessionDao.insert(timerSession)
            val insertedSession = timerSession.copy(id = id)

            // Delete the timer session
            timerSessionDao.delete(insertedSession)

            // Get all timer sessions
            val allSessions = timerSessionDao.getAllTimerSessions()

            // Verify the timer session was deleted
            assertEquals(0, allSessions.size)
        }

    @Test
    fun timerSessionDao_inserts_multiple_sessions_of_different_types() =
        runTest {
            // Create and insert three different types of timer sessions
            val pomodoroSession =
                TimerSessionEntity(
                    id = 0,
                    description = "Pomodoro 1",
                    startTime = System.currentTimeMillis(),
                    endTime = System.currentTimeMillis() + 25 * 60 * 1000L,
                    duration = 25 * 60 * 1000L,
                    type = TimerSessionType.POMODORO,
                    status = TimerSessionStatus.COMPLETED,
                )

            val shortBreakSession =
                TimerSessionEntity(
                    id = 0,
                    description = "Short Break 1",
                    startTime = System.currentTimeMillis() + 25 * 60 * 1000L,
                    endTime = System.currentTimeMillis() + 30 * 60 * 1000L,
                    duration = 5 * 60 * 1000L,
                    type = TimerSessionType.SHORT_BREAK,
                    status = TimerSessionStatus.COMPLETED,
                )

            val longBreakSession =
                TimerSessionEntity(
                    id = 0,
                    description = "Long Break 1",
                    startTime = System.currentTimeMillis() + 30 * 60 * 1000L,
                    endTime = System.currentTimeMillis() + 45 * 60 * 1000L,
                    duration = 15 * 60 * 1000L,
                    type = TimerSessionType.LONG_BREAK,
                    status = TimerSessionStatus.SKIPPED,
                )

            timerSessionDao.insert(pomodoroSession)
            timerSessionDao.insert(shortBreakSession)
            timerSessionDao.insert(longBreakSession)

            // Get all timer sessions
            val allSessions = timerSessionDao.getAllTimerSessions()

            // Verify all sessions were inserted
            assertEquals(3, allSessions.size)

            // Verify we have one of each type
            val types = allSessions.map { it.type }
            assertEquals(1, types.count { it == TimerSessionType.POMODORO })
            assertEquals(1, types.count { it == TimerSessionType.SHORT_BREAK })
            assertEquals(1, types.count { it == TimerSessionType.LONG_BREAK })
        }
}
