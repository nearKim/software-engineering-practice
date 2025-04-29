package observer_pattern.v1

// Interface for the time sink (e.g., DigitalClock)
interface TimeSink {
    fun setTime(
        hours: Int,
        minutes: Int,
        seconds: Int,
    )
}
