package observer_pattern.v3.test

import observer_pattern.v3.ClockObserver
import observer_pattern.v3.TimeSource

class MockTimeSource : TimeSource {
    private val observers = mutableListOf<ClockObserver>()

    override fun addObserver(observer: ClockObserver) {
        observers.add(observer)
    }

    fun setTime(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ) {
        observers.forEach { it.update(hours, minutes, seconds) }
    }
}
