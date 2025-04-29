package observer_pattern.v5

// Example Clock class inheriting from TimeSource
// FIXME: Clock이 왜 TimeSource를 상속하게 되어 register, notify와 같은 책임을 져야 하나?
open class Clock : TimeSource() {
    fun tic(
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        notify(hours, mins, secs)
    }
}
