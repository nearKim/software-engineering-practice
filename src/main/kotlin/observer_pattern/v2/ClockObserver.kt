package observer_pattern.v2

// Interface for observers of time changes
interface ClockObserver {
    fun update(
        hours: Int,
        minutes: Int,
        seconds: Int,
    )
}
