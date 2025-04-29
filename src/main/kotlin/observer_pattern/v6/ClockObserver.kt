package observer_pattern.v6

interface ClockObserver {
    fun update(
        hours: Int,
        mins: Int,
        secs: Int,
    )
}
