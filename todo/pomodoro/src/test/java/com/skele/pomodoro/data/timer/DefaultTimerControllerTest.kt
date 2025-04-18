package com.skele.pomodoro.data.timer

import com.skele.pomodoro.TestDispatcherProvider
import com.skele.pomodoro.domain.timer.TimerController
import com.skele.pomodoro.domain.timer.TimerState
import com.skele.pomodoro.util.DispatchersProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DefaultTimerControllerTest {
    private lateinit var timerController: TimerController
    private lateinit var testScope: TestScope
    private lateinit var dispatchersProvider: DispatchersProvider

    @Before
    fun setup() {
        dispatchersProvider = TestDispatcherProvider()
        testScope = TestScope(dispatchersProvider.default)

        timerController = DefaultTimerController(testScope, dispatchersProvider)
    }

    @After
    fun tearDown() {
        testScope.cancel()
    }

    @Test
    fun testSetTimer_setsInitialStateCorrectly() =
        testScope.runTest {
            timerController.setTimer(5000L)
            val state = timerController.timerState.value
            assertTrue(state is TimerState.Idle)
            assertEquals(5000L, state.data.remainingTime)
            assertEquals(5000L, state.data.totalTime)
        }

    @Test
    fun testStart_countsDownCorrectly() =
        testScope.runTest {
            timerController.setTimer(3000L)
            timerController.start()
            advanceTimeBy(2000L)
            runCurrent()

            val state = timerController.timerState.value
            assertTrue(state is TimerState.Running)
            assertEquals(1000L, state.data.remainingTime)
        }

    @Test
    fun testPause_stopsCountdown() =
        testScope.runTest {
            timerController.setTimer(3000L)
            timerController.start()
            advanceTimeBy(2000L)
            runCurrent()
            timerController.pause()
            val pausedState = timerController.timerState.value
            assertTrue(pausedState is TimerState.Paused)
            val timeLeft = pausedState.data.remainingTime

            advanceTimeBy(2000L)
            runCurrent()
            assertEquals(timeLeft, timerController.timerState.value.data.remainingTime)
        }

    @Test
    fun testResume_continuesCountdown() =
        testScope.runTest {
            timerController.setTimer(3000L)
            timerController.start()
            advanceTimeBy(2000L)
            runCurrent()
            timerController.pause()
            val pausedTime = timerController.timerState.value.data.remainingTime
            timerController.resume()
            advanceTimeBy(1000L)
            runCurrent()

            val state = timerController.timerState.value
            assertTrue(state is TimerState.Finished)
            assertEquals((pausedTime - 1000L).coerceAtLeast(0L), state.data.remainingTime)
        }

    @Test
    fun testReset_resetsTimerToInitial() =
        testScope.runTest {
            timerController.setTimer(5000L)
            timerController.start()
            advanceTimeBy(2000L)
            runCurrent()
            timerController.reset()

            val state = timerController.timerState.value
            assertTrue(state is TimerState.Idle)
            assertEquals(5000L, state.data.remainingTime)
        }

    @Test
    fun testTimerFinishesCorrectly() =
        testScope.runTest {
            timerController.setTimer(2000L)
            timerController.start()
            advanceTimeBy(2000L)
            runCurrent()

            val state = timerController.timerState.value
            assertTrue(state is TimerState.Finished)
            assertEquals(0L, state.data.remainingTime)
        }
}
