package observer_pattern.v2

// Interface for the time source
interface TimeSource {
    fun setObserver(observer: ClockObserver)
}
