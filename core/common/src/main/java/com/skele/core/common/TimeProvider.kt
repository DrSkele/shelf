package com.skele.core.common

interface TimeProvider {
    fun currentTimeMillis(): Long
}

class DefaultTimeProvider : TimeProvider {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}