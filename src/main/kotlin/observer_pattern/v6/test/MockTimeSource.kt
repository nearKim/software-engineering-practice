package observer_pattern.v6.test

import observer_pattern.v6.ClockObserver
import observer_pattern.v6.TimeSource
import observer_pattern.v6.TimeSourceImpl

class MockTimeSource : TimeSource {
    private val impl = TimeSourceImpl()

    fun setTime(
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        impl.notify(hours, mins, secs)
    }

    override fun registerObserver(observer: ClockObserver) {
        impl.registerObserver(observer)
    }
}
