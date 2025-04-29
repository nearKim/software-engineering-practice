package observer_pattern.v6

interface TimeSource {
    fun registerObserver(observer: ClockObserver)
}
