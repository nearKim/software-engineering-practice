package observer_pattern.v2

// Class that drives time updates from source to sink
class ClockDriver(
    source: TimeSource,
    private val sink: TimeSink,
) : ClockObserver {
    init {
        source.setObserver(this)
    }

    override fun update(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ) {
        sink.setTime(hours, minutes, seconds)
    }
}
