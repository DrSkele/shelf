package com.skele.core.timer

import com.skele.core.common.TimeProvider
import java.util.concurrent.atomic.AtomicLong

class TestTimeProvider : TimeProvider {
    private val currentTime = AtomicLong(0)

    fun setTime(time: Long) {
        currentTime.set(time)
    }

    fun advanceTime(deltaTime: Long) {
        currentTime.addAndGet(deltaTime)
    }

    override fun currentTimeMillis(): Long = currentTime.get()
}
