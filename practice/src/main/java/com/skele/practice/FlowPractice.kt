package com.skele.practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale

val formatter = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

fun main() {
   val countDownFlow = flow{
       val initialValue = 10
       var currentValue = initialValue

       println("emitting value : $currentValue at ${formatter.format(System.currentTimeMillis())}")
       emit(initialValue)

       while(currentValue > 0) {
           delay(1000L)
           currentValue--
           println("emitting value : $currentValue at ${formatter.format(System.currentTimeMillis())}")
           emit(currentValue)
       }
   }

    collectFlow(countDownFlow)
    collectLatestFlow(countDownFlow)
}

fun collectFlow(countDownFlow: Flow<Int>) {
    runBlocking {
        countDownFlow.collect{ time ->
            delay(1500L)
            println("collected value : $time at ${formatter.format(System.currentTimeMillis())}")
        }
    }
}

fun collectLatestFlow(countDownFlow: Flow<Int>) {
    runBlocking {
        countDownFlow.collectLatest{ time ->
            delay(1500L)
            println("collected latest value : $time at ${formatter.format(System.currentTimeMillis())}")
        }
    }
}