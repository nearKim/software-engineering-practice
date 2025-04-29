package observer_pattern.v2

// Interface for the time sink
interface TimeSink {
    fun setTime(
        hours: Int,
        minutes: Int,
        seconds: Int,
    )
}
