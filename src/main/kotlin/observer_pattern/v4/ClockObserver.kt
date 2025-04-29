package observer_pattern.v4

interface ClockObserver {
    fun update(
        hours: Int,
        minutes: Int,
        seconds: Int,
    )
}
