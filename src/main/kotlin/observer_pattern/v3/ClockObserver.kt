package observer_pattern.v3

interface ClockObserver {
    fun update(
        hours: Int,
        minutes: Int,
        seconds: Int,
    )
}
