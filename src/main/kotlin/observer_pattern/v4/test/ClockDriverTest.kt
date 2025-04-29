package observer_pattern.v4.test

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ClockDriverTest {
    private lateinit var source: MockTimeSource
    private lateinit var sink: MockTimeSink

    @Before
    fun setUp() {
        source = MockTimeSource()
        sink = MockTimeSink()
        source.registerObserver(sink)
    }

    private fun assertSinkEquals(
        sink: MockTimeSink,
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        Assert.assertEquals(hours, sink.getHours())
        Assert.assertEquals(mins, sink.getMinutes())
        Assert.assertEquals(secs, sink.getSeconds())
    }

    @Test
    fun testTimeChange() {
        source.setTime(3, 4, 5)
        assertSinkEquals(sink, 3, 4, 5)

        source.setTime(7, 8, 9)
        assertSinkEquals(sink, 7, 8, 9)
    }

    @Test
    fun testMultipleSinks() {
        val sink2 = MockTimeSink()
        source.registerObserver(sink2)

        source.setTime(12, 13, 14)
        assertSinkEquals(sink, 12, 13, 14)
        assertSinkEquals(sink2, 12, 13, 14)
    }
}
