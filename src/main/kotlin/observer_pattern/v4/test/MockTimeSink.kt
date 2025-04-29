package observer_pattern.v4.test

import observer_pattern.v4.ClockObserver

class MockTimeSink : ClockObserver {
    private var hours: Int = 0
    private var minutes: Int = 0
    private var seconds: Int = 0

    fun getHours(): Int = hours

    fun getMinutes(): Int = minutes

    fun getSeconds(): Int = seconds

    override fun update(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ) {
        this.hours = hours
        this.minutes = minutes
        this.seconds = seconds
    }
}
