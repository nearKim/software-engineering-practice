package observer_pattern.v5.test

import observer_pattern.v5.ClockObserver

class MockTimeSink : ClockObserver {
    override fun update(
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        println("Time updated: $hours:$mins:$secs")
    }
}
