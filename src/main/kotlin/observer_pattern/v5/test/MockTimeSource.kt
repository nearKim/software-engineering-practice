package observer_pattern.v5.test

import observer_pattern.v5.TimeSource

class MockTimeSource : TimeSource() {
    fun setTime(
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        notify(hours, mins, secs)
    }
}
