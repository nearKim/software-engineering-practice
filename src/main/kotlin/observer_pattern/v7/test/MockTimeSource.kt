package observer_pattern.v7.test

import observer_pattern.v7.Subject
import observer_pattern.v7.TimeSource

/**
 * Test double that *pushes* a change notification and
 * lets observers *pull* the current time.
 */
class MockTimeSource :
    Subject(),
    TimeSource {
    private var h = 0
    private var m = 0
    private var s = 0

    /** Simulate the clock ticking. */
    fun setTime(
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        h = hours
        m = mins
        s = secs
        notifyObservers()
    }

    // — TimeSource — ---------------------------------------------------------
    override fun hours() = h

    override fun minutes() = m

    override fun seconds() = s
}
