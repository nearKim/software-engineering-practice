package observer_pattern.v2.test

import observer_pattern.v2.ClockObserver
import observer_pattern.v2.TimeSource

class MockTimeSource : TimeSource {
    private var observer: ClockObserver? = null

    override fun setObserver(observer: ClockObserver) {
        this.observer = observer
    }

    fun setTime(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ) {
        observer?.update(hours, minutes, seconds)
    }
}
