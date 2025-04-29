package observer_pattern.naive

import kotlinx.coroutines.*

suspend fun simulateTics(clock: Clock) {
    while (true) {
        delay(1) // Simulate 1ms tic
        clock.tic()
    }
}

suspend fun displayTime(clock: Clock) {
    var lastSeconds = -1
    while (true) {
        val currentSeconds = clock.seconds
        if (currentSeconds != lastSeconds) {
            val hours = clock.hours
            val minutes = clock.minutes
            val seconds = clock.seconds
            println(String.format("%02d:%02d:%02d", hours, minutes, seconds))
            lastSeconds = currentSeconds
        }
        delay(100) // Check every 100ms
    }
}

fun main() =
    runBlocking {
        val clock = Clock()

        launch { simulateTics(clock) } // Simulate OS tics
        launch { displayTime(clock) } // Display time efficiently

        // Keep the program running
        while (true) {
            delay(1000)
        }
    }
