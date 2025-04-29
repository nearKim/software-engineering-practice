package observer_pattern.v7.test

import observer_pattern.v7.Observer
import observer_pattern.v7.TimeSource

/**
 * Test double that *pulls* the time from its TimeSource
 * whenever it receives an `update()` call.
 */
class MockTimeSink(
    private val source: TimeSource,
) : Observer {
    private var h = 0
    private var m = 0
    private var s = 0

    // read access for the unit tests
    fun hours() = h

    fun minutes() = m

    fun seconds() = s

    // — Observer —
    override fun update() {
        h = source.hours()
        m = source.minutes()
        s = source.seconds()
    }
}
