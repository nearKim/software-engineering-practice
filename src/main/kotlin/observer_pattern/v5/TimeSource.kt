package observer_pattern.v5

abstract class TimeSource {
    private val observers = mutableListOf<ClockObserver>()

    protected fun notify(
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        observers.forEach { it.update(hours, mins, secs) }
    }

    fun registerObserver(observer: ClockObserver) {
        observers.add(observer)
    }
}
