package com.skele.core.model.common

import kotlinx.serialization.Serializable
import java.time.DayOfWeek

@kotlinx.serialization.Serializable
enum class ScheduleType {
    ONE_TIME,
    EVERYDAY,
    SPECIFIC_DAYS,
}

@Serializable
data class Schedule(
    val type: ScheduleType,
    val days: List<DayOfWeek> = emptyList(),
) {
    companion object {
        val ONE_TIME = Schedule(ScheduleType.ONE_TIME)

        val EVERYDAY = Schedule(ScheduleType.EVERYDAY)

        val WEEKDAYS =
            Schedule(
                ScheduleType.SPECIFIC_DAYS,
                listOf(
                    DayOfWeek.MONDAY,
                    DayOfWeek.TUESDAY,
                    DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY,
                    DayOfWeek.FRIDAY,
                ),
            )

        val WEEKEND =
            Schedule(ScheduleType.SPECIFIC_DAYS, listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY))

        fun specificDays(vararg days: DayOfWeek) = Schedule(ScheduleType.SPECIFIC_DAYS, days.toList())
    }

    fun isActiveOn(day: DayOfWeek): Boolean =
        when (type) {
            ScheduleType.ONE_TIME -> false
            ScheduleType.EVERYDAY -> true
            ScheduleType.SPECIFIC_DAYS -> day in days
        }
}

