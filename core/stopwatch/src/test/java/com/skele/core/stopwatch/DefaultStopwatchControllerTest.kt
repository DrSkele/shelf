package com.skele.core.stopwatch

import com.skele.core.common.DispatchersProvider
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.TestScope
import org.junit.After
import org.junit.Before

class StopwatchControllerTest {
    private lateinit var stopwatchController: StopwatchController
    private lateinit var testScope: TestScope
    private lateinit var dispatchersProvider: DispatchersProvider
    private lateinit var testTimeProvider: TestTimeProvider

    @Before
    fun setup() {
        dispatchersProvider = TestDispatcherProvider()
        testScope = TestScope(dispatchersProvider.Default)
        testTimeProvider = TestTimeProvider()

        stopwatchController = DefaultStopwatchController(testScope, dispatchersProvider, testTimeProvider)
    }

    @After
    fun tearDown() {
        // First cancel any running timer jobs
        stopwatchController.pause()
        stopwatchController.cleanup()
        // Then cancel the test scope
        testScope.cancel()
    }
}