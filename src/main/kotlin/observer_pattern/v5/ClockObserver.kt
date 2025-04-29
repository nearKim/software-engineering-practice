package observer_pattern.v5

interface ClockObserver {
    fun update(
        hours: Int,
        mins: Int,
        secs: Int,
    )
}
