package observer_pattern.v4.test

import observer_pattern.v4.ClockObserver
import observer_pattern.v4.TimeSource

class MockTimeSource : TimeSource {
    private val observers = mutableListOf<ClockObserver>()

    override fun registerObserver(observer: ClockObserver) {
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
