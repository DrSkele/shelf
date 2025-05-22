package com.skele.core.timer

import com.skele.core.common.DispatchersProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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
    private lateinit var testTimeProvider: TestTimeProvider

    @Before
    fun setup() {
        dispatchersProvider = TestDispatcherProvider()
        testScope = TestScope(dispatchersProvider.default)
        testTimeProvider = TestTimeProvider()

        timerController = DefaultTimerController(testScope, dispatchersProvider, testTimeProvider)
    }

    @After
    fun tearDown() {
        // First cancel any running timer jobs
        timerController.pause()
        timerController.reset()
        // Then cancel the test scope
        testScope.cancel()
    }

    @Test
    fun `state initialized correctly`() =
        testScope.runTest {
            timerController.setTimer(5000L)
            val state = timerController.timerState.value
            assertTrue(state is TimerState.Idle)
            assertEquals(5000L, state.data.remainingTime)
            assertEquals(5000L, state.data.totalTime)
        }

    @Test
    fun `timer starts correctly`() =
        testScope.runTest {
            timerController.setTimer(3000L)
            timerController.start()

            testTimeProvider.setTime(2000L)
            advanceTimeBy(2000L)
            runCurrent()

            // Ensure the job completes by pausing the timer at the end
            val state = timerController.timerState.value
            assertTrue(state is TimerState.Running)
            assertEquals(1000L, state.data.remainingTime)

            // Important: ensure cleanup to prevent hanging coroutines
            timerController.cleanup()
        }

    @Test
    fun `timer pauses correctly`() =
        testScope.runTest {
            timerController.setTimer(3000L)
            timerController.start()

            testTimeProvider.advanceTime(2000L)
            advanceTimeBy(2000L)
            runCurrent()

            timerController.pause()
            val pausedState = timerController.timerState.value
            assertTrue(pausedState is TimerState.Paused)
            val timeLeft = pausedState.data.remainingTime

            testTimeProvider.advanceTime(4000L)
            advanceTimeBy(4000L)
            runCurrent()

            assertEquals(timeLeft, timerController.timerState.value.data.remainingTime)
        }

    @Test
    fun `remaining timer is updated correctly`() =
        testScope.runTest {
            timerController.setTimer(3000L)
            timerController.start()

            testTimeProvider.advanceTime(2000L)
            advanceTimeBy(2000L)
            runCurrent()

            timerController.pause()
            val pausedTime = timerController.timerState.value
            assertTrue(pausedTime is TimerState.Paused)
            assertEquals(1000L, pausedTime.data.remainingTime)
            timerController.resume()

            testTimeProvider.advanceTime(3000L)
            advanceTimeBy(3000L)
            runCurrent()

            val state = timerController.timerState.value
            assertTrue(state is TimerState.Finished)
            assertEquals(0L, state.data.remainingTime)
        }

    @Test
    fun `timer is reset correctly`() =
        testScope.runTest {
            timerController.setTimer(5000L)
            timerController.start()

            testTimeProvider.setTime(2000L)
            advanceTimeBy(100)
            runCurrent()

            timerController.reset()

            val state = timerController.timerState.value
            assertTrue(state is TimerState.Idle)
            assertEquals(5000L, state.data.remainingTime)
        }

    @Test
    fun `timer finishes correctly`() =
        testScope.runTest {
            timerController.setTimer(2000L)
            timerController.start()

            testTimeProvider.setTime(2000L)
            advanceTimeBy(100)
            runCurrent()

            val state = timerController.timerState.value
            assertTrue(state is TimerState.Finished)
            assertEquals(0L, state.data.remainingTime)
        }

    @Test
    fun `timer does not emit multiple Finished states`() =
        testScope.runTest {
            // Arrange
            val stateEmissions = mutableListOf<TimerState>()
            val job =
                launch {
                    timerController.timerState.collect { state ->
                        stateEmissions.add(state)
                    }
                }

            timerController.setTimer(300L) // Short timer to reach end quickly
            runCurrent()

            // Act
            timerController.start()

            // Let the timer run until completion
            testTimeProvider.setTime(350L)
            advanceTimeBy(100)
            runCurrent()

            // Assert
            val finishedStates = stateEmissions.filterIsInstance<TimerState.Finished>()

            assertEquals("TimerState.Finished should only be emitted once", 1, finishedStates.size)

            // Verify the sequence of states
            assertTrue("First state should be Idle", stateEmissions.first() is TimerState.Idle)
            val runningStates = stateEmissions.filterIsInstance<TimerState.Running>()
            assertTrue("Should have Running states", runningStates.isNotEmpty())
            assertTrue(
                "Last state should be Finished",
                stateEmissions.last() is TimerState.Finished,
            )

            // Cleanup
            job.cancel()
        }

    @Test
    fun `timer boundary test with 100ms delay granularity`() =
        testScope.runTest {
            // Test timers that are exact multiples of 100ms and those that aren't
            val testCases =
                listOf(
                    100L, // Exact multiple
                    200L, // Exact multiple
                    150L, // Not exact multiple
                    350L, // Not exact multiple
                    101L, // Just over 100ms
                    99L, // Just under 100ms
                )

            for (timerMs in testCases) {
                println("Testing with timer: $timerMs ms")
                val stateEmissions = mutableListOf<TimerState>()
                val job =
                    launch {
                        timerController.timerState.collect { state ->
                            stateEmissions.add(state)
                            println("State: ${state::class.simpleName}, Remaining: ${state.remainingTime}")
                        }
                    }

                timerController.setTimer(timerMs)
                runCurrent()

                timerController.start()

                // Advance time to completion
                testTimeProvider.setTime(timerMs + 50)
                advanceTimeBy(100)
                runCurrent()

                // Check for multiple Finished states
                val finishedStates = stateEmissions.filterIsInstance<TimerState.Finished>()
                assertEquals(
                    "Timer $timerMs ms should emit Finished exactly once",
                    1,
                    finishedStates.size,
                )

                // Verify the remaining time is 0 in the Finished state
                finishedStates.first().let { finished ->
                    assertEquals(
                        "Remaining time should be 0 in Finished state",
                        0L,
                        finished.remainingTime,
                    )
                }

                // Check if any state comes after Finished
                val finishedIndex = stateEmissions.indexOfFirst { it is TimerState.Finished }
                assertTrue(
                    "Finished should be the last state",
                    finishedIndex == stateEmissions.size - 1,
                )

                job.cancel()
                // Reset for next test
                timerController.reset()
                testTimeProvider.setTime(0)
                runCurrent()
            }
        }

    @Test
    fun `timer does not emit multiple Finished states in different scenarios`() =
        testScope.runTest {
            // Test 1: Normal timer completion
            val normalFlowEmissions = mutableListOf<TimerState>()
            val normalJob =
                launch {
                    timerController.timerState.collect { state ->
                        normalFlowEmissions.add(state)
                    }
                }

            timerController.setTimer(200L)
            runCurrent()
            timerController.start()
            testTimeProvider.setTime(250L)
            advanceTimeBy(100)
            runCurrent()
            normalJob.cancel()

            // Test 2: Pause and resume to completion
            val pauseResumeEmissions = mutableListOf<TimerState>()
            val pauseResumeJob =
                launch {
                    timerController.timerState.collect { state ->
                        pauseResumeEmissions.add(state)
                    }
                }

            timerController.setTimer(200L)
            runCurrent()
            testTimeProvider.setTime(0)
            timerController.start()
            testTimeProvider.setTime(100L)
            advanceTimeBy(100)
            runCurrent()
            timerController.pause()
            runCurrent()
            testTimeProvider.setTime(150L) // Time advance while paused
            advanceTimeBy(100)
            runCurrent()
            timerController.resume()
            testTimeProvider.setTime(250L) // Should finish
            advanceTimeBy(100)
            runCurrent()
            pauseResumeJob.cancel()

            // Test 3: Multiple start attempts after finishing
            val multipleStartEmissions = mutableListOf<TimerState>()
            val multipleStartJob =
                launch {
                    timerController.timerState.collect { state ->
                        multipleStartEmissions.add(state)
                    }
                }

            timerController.setTimer(200L)
            runCurrent()
            testTimeProvider.setTime(0)
            timerController.start()
            testTimeProvider.setTime(300L)
            advanceTimeBy(100)
            runCurrent()

            // Try to start again after finishing
            timerController.start()
            advanceTimeBy(100)
            runCurrent()

            multipleStartJob.cancel()

            // Assertions
            val normalFinishedCount = normalFlowEmissions.count { it is TimerState.Finished }
            assertEquals("Normal flow: Finished should only appear once", 1, normalFinishedCount)

            val pauseResumeFinishedCount = pauseResumeEmissions.count { it is TimerState.Finished }
            assertEquals(
                "Pause/Resume flow: Finished should only appear once",
                1,
                pauseResumeFinishedCount,
            )

            val multipleStartFinishedCount =
                multipleStartEmissions.count { it is TimerState.Finished }
            assertEquals(
                "Multiple start flow: Finished should only appear once",
                1,
                multipleStartFinishedCount,
            )

            // Additional check: ensure no state comes after Finished
            val afterFinishedStates =
                multipleStartEmissions.dropWhile { it !is TimerState.Finished }.drop(1)
            assertTrue("No states should follow Finished", afterFinishedStates.isEmpty())
        }

    @Test
    fun `timer race condition test when exactly reaching zero`() =
        testScope.runTest {
            // Test with different timer intervals to check for race conditions
            val intervals = listOf(100L, 250L, 333L, 500L)

            for (interval in intervals) {
                println("Testing with interval: $interval ms")
                val stateEmissions = mutableListOf<TimerState>()
                val job =
                    launch {
                        timerController.timerState.collect { state ->
                            stateEmissions.add(state)
                            println("State: ${state::class.simpleName}, Remaining: ${state.remainingTime}")
                        }
                    }

                // Set timer that will divide the interval evenly
                timerController.setTimer(interval)
                runCurrent()
                testTimeProvider.setTime(0)
                timerController.start()

                // Advance time in small steps to check for transition issues
                for (step in 0 until 5) {
                    val timeAdvance = interval / 4
                    testTimeProvider.setTime((step + 1) * timeAdvance)
                    advanceTimeBy(100L)
                    runCurrent()
                }

                // Make sure we're past the interval
                testTimeProvider.setTime(interval + 50)
                advanceTimeBy(100L)
                runCurrent()

                // Count Finished states
                val finishedCount = stateEmissions.count { it is TimerState.Finished }
                assertEquals(
                    "Timer with interval $interval ms should emit Finished only once",
                    1,
                    finishedCount,
                )

                // Clean up before next iteration
                job.cancel()
                timerController.reset()
                testTimeProvider.setTime(0)
                runCurrent()
            }
        }
}
