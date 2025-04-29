package observer_pattern.naive

class Clock {
    private var milliseconds: Long = 0

    val seconds: Int
        get() = ((milliseconds / 1000) % 60).toInt()

    val minutes: Int
        get() = ((milliseconds / (1000 * 60)) % 60).toInt()

    val hours: Int
        get() = ((milliseconds / (1000 * 60 * 60)) % 24).toInt()

    fun tic() {
        milliseconds += 1
    }
}
