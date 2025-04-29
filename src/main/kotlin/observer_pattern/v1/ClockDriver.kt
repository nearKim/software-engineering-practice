package observer_pattern.v1

// Class that drives time updates from source to sink
class ClockDriver(
    private val source: TimeSource,
    private val sink: TimeSink,
) {
    init {
        source.setDriver(this)
    }

    fun update(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ) {
        sink.setTime(hours, minutes, seconds)
    }
}
