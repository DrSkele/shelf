package com.skele.core.stopwatch

import com.skele.core.common.TimeProvider

// Test implementation
class TestTimeProvider : TimeProvider {
    private var currentTime = 0L

    override fun currentTimeMillis(): Long = currentTime

    fun setCurrentTime(time: Long) {
        currentTime = time
    }

    fun advanceTimeBy(milliseconds: Long) {
        currentTime += milliseconds
    }
}