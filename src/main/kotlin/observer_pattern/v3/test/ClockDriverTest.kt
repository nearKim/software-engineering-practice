package observer_pattern.v3.test

import org.junit.Assert
import org.junit.Test

class ClockDriverTest {
    @Test
    fun testTimeChange() {
        val source = MockTimeSource()
        val sink = MockTimeSink()
        source.addObserver(sink)

        // Test first time update
        source.setTime(3, 4, 5)
        Assert.assertEquals(3, sink.getHours())
        Assert.assertEquals(4, sink.getMinutes())
        Assert.assertEquals(5, sink.getSeconds())

        // Test second time update
        source.setTime(7, 8, 9)
        Assert.assertEquals(7, sink.getHours())
        Assert.assertEquals(8, sink.getMinutes())
        Assert.assertEquals(9, sink.getSeconds())
    }
}
