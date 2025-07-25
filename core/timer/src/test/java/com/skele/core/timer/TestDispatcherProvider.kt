package com.skele.core.timer

import com.skele.core.common.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

class TestDispatcherProvider : DispatchersProvider {
    val testDispatcher = StandardTestDispatcher()

    override val main: CoroutineDispatcher
        get() = testDispatcher
    override val io: CoroutineDispatcher
        get() = testDispatcher
    override val default: CoroutineDispatcher
        get() = testDispatcher
}