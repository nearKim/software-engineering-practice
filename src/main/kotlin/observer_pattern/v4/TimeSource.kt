package observer_pattern.v4

interface TimeSource {
    fun registerObserver(observer: ClockObserver)
}
