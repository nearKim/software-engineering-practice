package observer_pattern.v3

interface TimeSource {
    fun addObserver(observer: ClockObserver)
}
