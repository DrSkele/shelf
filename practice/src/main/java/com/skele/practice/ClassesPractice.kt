package com.skele.practice

import com.google.gson.Gson
import java.io.File
import java.time.ZonedDateTime
import kotlinx.serialization.*
import kotlinx.serialization.json.*

fun main() {
//    normalClass()
//    dataClasses()
//    objectClasses()
//    jsonPractice()
//    gsonPractice()
//    enumClasses()
}

fun normalClass() {
    val file = File("hello.txt")
    val sessionStorage1 = SessionStorage(file)
    var sessionStorage2 = SessionStorage(file)

    // compare reference
    println(sessionStorage1 == sessionStorage2)

    sessionStorage2 = sessionStorage1

    println(sessionStorage1 == sessionStorage2)
}

class SessionStorage(
    private val file: File
) {
    var counter = 0

    fun save(value: String) {
        // ...
    }
}

fun dataClasses() {
    val sessionStorage1 = SessionStorage(File("hello.txt"))

    val person1 = Person(
        name = "John Smith",
        birthDate = "1997-06-13",
        email = "somestring@gmail.com"
    )
    var person2 = Person(
        name = "John Smith",
        birthDate = "1997-06-13",
        email = "somestring@gmail.com"
    )

    // toString
    println(sessionStorage1)
    println(person1)

    // compare values
    println(person1 == person2)

    person2 = Person(
        name = "Not John Smith",
        birthDate = "1997-06-13",
        email = "somestring@gmail.com"
    )

    println(person1 == person2)
}

data class Person(
    val name: String,
    val birthDate: String,
    val email: String
) {
    // data class overrides three functions
    // equals, hashCode, toString
}

fun objectClasses() {

    // cannot instantiate
    //val dateutil = DateUtil()

    DateUtil.format(ZonedDateTime.now())
    DateTimeUtil.format(ZonedDateTime.now())

    println(DateUtil)
    println(DateTimeUtil)
}

object DateUtil {
    fun format(dateTime: ZonedDateTime): String {
        return ".."
    }
}

data object DateTimeUtil {
    fun format(dateTime: ZonedDateTime): String {
        return ".."
    }
}

fun jsonPractice() {
    val original = SerializableObject

    val json = Json.encodeToString(original)

    val deserialized1 = Json.decodeFromString<SerializableObject>(json)
    println(deserialized1)
    val deserialized2 = Json.decodeFromString<SerializableObject>(json)
    println(deserialized2)

    // true
    println(deserialized1 == deserialized2)
}

fun gsonPractice() {
    val original = SerializableObject

    val gson = Gson()
    val json = gson.toJson(original)

    val deserialized1 = gson.fromJson(json, SerializableObject::class.java)
    println(deserialized1)

    val deserialized2 = gson.fromJson(json, SerializableObject::class.java)
    println(deserialized2)

    // gson makes duplicate of object class instance
    // false
    println(deserialized1 == deserialized2)
}

@Serializable
object SerializableObject{
    var someValue = "some value"
}

fun enumClasses() {
    val response = HttpStatus.OK

    println(response.code)
    println(response.message)
    println(response.toResponseString())

    // enum class can be iterated
    HttpStatus.entries.forEach {
        println(it.name)
    }
}

// enum class can have constructor
enum class HttpStatus(val code: Int, val message: String) {
    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    // enum class also can have functions
    fun toResponseString(): String {
        return "$code $message"
    }
}

fun sealedClasses() {
    // sealedCla
    val resultA = NetworkResult.Success("Success A")
    val resultB = NetworkResult.Success("Success B")
}

// difference between sealed class and enum class
// enum class can have only one instance and has constant values
// sealed class can have instances of independent member
sealed class NetworkResult {
    data class Success(val data: String) : NetworkResult()
    data class Error(val throwable: Throwable) : NetworkResult()
    data object Empty : NetworkResult()
}

fun abstractClasses() {
    val sensor = Accelerometer()
    val sensor2 = CustomAccelerometer()
}

// cannot instantiate
// can inherit
abstract class Sensor {
    abstract val name: String
    abstract fun startListening(onNewValue: (Double) -> Unit)
}

// can instantiate
// can inherit
open class Accelerometer : Sensor() {
    override val name: String
        get() = "Accelerometer"

    override fun startListening(onNewValue: (Double) -> Unit) {
        // ..
    }
}

class CustomAccelerometer : Accelerometer() {
    override val name: String
        get() = "Custom Accelerometer"
}

fun anonymousClasses() {
    val sensor = object : Sensor() {
        override val name: String
            get() = "Custom Accelerometer"

        override fun startListening(onNewValue: (Double) -> Unit) {
            TODO("Not yet implemented")
        }
    }
}

fun valueClasses() {
    val email = Email("somestring@gmail.com")
}

// value class acts as a wrapper
@JvmInline
value class Email(val email: String) {
    init {
        if(!email.contains("@")) {
            throw IllegalArgumentException("Invalid email")
        }
    }
}

fun annotationClasses() {

}

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.CONSTRUCTOR
)
annotation class Deprecated

@Deprecated
class deprecatedClass{

}

fun innerClasses() {


}

class OuterClass {
    val member: Int = 1

    inner class InnerClass {

        // inner class has access to outer class member
        fun getMember() {
            println(member)
        }
    }

    class NestedClass {

        // cannot access outer class member
        fun getMember() {
            //println(member)
        }
    }
}