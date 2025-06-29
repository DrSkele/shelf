package com.skele.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.time.DayOfWeek

@Entity(tableName = ToDoEntity.tableName)
@TypeConverters(ToDoDataConverter::class, ScheduleConverter::class)
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val description: String,
    val isDone: Boolean = false,
    val schedule: Schedule,
    val timeStamp: Long,
    val data: ToDoData,
) {
    companion object {
        const val tableName = "todo"
    }
}

@Serializable
sealed class ToDoData {
    @Serializable
    @SerialName("simple")
    data object Default : ToDoData()

    @Serializable
    @SerialName("timed")
    data class Timed(
        val setTime: Long,
        val elapsedTime: Long,
    ) : ToDoData()
}

class ToDoDataConverter {
    @TypeConverter
    fun fromToDoData(data: ToDoData): String = Json.encodeToString(ToDoData.serializer(), data)

    @TypeConverter
    fun toToDoData(data: String): ToDoData = Json.decodeFromString(ToDoData.serializer(), data)
}

// Custom serializer for DayOfWeek
object DayOfWeekSerializer : KSerializer<DayOfWeek> {
    override val descriptor = PrimitiveSerialDescriptor("DayOfWeek", PrimitiveKind.INT)

    override fun serialize(
        encoder: Encoder,
        value: DayOfWeek,
    ) = encoder.encodeInt(value.value)

    override fun deserialize(decoder: Decoder): DayOfWeek = DayOfWeek.of(decoder.decodeInt())
}

class ScheduleConverter {
    private val json =
        Json {
            serializersModule =
                SerializersModule {
                    contextual(DayOfWeek::class, DayOfWeekSerializer)
                }
        }

    @TypeConverter
    fun fromSchedule(schedule: Schedule): String = json.encodeToString(schedule)

    @TypeConverter
    fun toSchedule(jsonString: String): Schedule = json.decodeFromString(jsonString)
}

@Serializable
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
