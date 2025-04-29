package observer_pattern.v1.test

import observer_pattern.v1.ClockDriver
import observer_pattern.v1.TimeSource

// Mock implementation of TimeSource for testing
class MockTimeSource : TimeSource {
    private var driver: ClockDriver? = null

    override fun setDriver(driver: ClockDriver) {
        this.driver = driver
    }

    fun setTime(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ) {
        driver?.update(hours, minutes, seconds)
    }
}
