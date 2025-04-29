package observer_pattern.v7.test

import org.junit.*

class ObserverTest {
    private lateinit var source: MockTimeSource
    private lateinit var sink: MockTimeSink

    @Before
    fun setUp() {
        source = MockTimeSource()
        sink = MockTimeSink(source)
        source.registerObserver(sink)
    }

    private fun assertSinkEquals(
        expH: Int,
        expM: Int,
        expS: Int,
    ) {
        Assert.assertEquals(expH, sink.hours())
        Assert.assertEquals(expM, sink.minutes())
        Assert.assertEquals(expS, sink.seconds())
    }

    @Test
    fun timeChangeIsPropagated() {
        source.setTime(3, 4, 5)
        assertSinkEquals(3, 4, 5)

        source.setTime(7, 8, 9)
        assertSinkEquals(7, 8, 9)
    }

    @Test
    fun multipleSinksReceiveSameUpdate() {
        val sink2 = MockTimeSink(source)
        source.registerObserver(sink2)

        source.setTime(12, 13, 14)
        assertSinkEquals(12, 13, 14)
        Assert.assertEquals(12, sink2.hours())
        Assert.assertEquals(13, sink2.minutes())
        Assert.assertEquals(14, sink2.seconds())
    }
}
