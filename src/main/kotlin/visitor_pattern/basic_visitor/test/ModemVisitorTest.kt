package visitor_pattern.basic_visitor.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import visitor_pattern.basic_visitor.modem.impl.ErnieModem
import visitor_pattern.basic_visitor.modem.impl.HayesModem
import visitor_pattern.basic_visitor.modem.impl.UnixModemConfigurator
import visitor_pattern.basic_visitor.modem.impl.ZoomModem

class ModemVisitorTest {
    private lateinit var unixConfigurator: UnixModemConfigurator
    private lateinit var hayes: HayesModem
    private lateinit var zoom: ZoomModem
    private lateinit var ernie: ErnieModem

    @BeforeEach
    fun setUp() {
        unixConfigurator = UnixModemConfigurator()
        hayes = HayesModem()
        zoom = ZoomModem()
        ernie = ErnieModem()
    }

    @Test
    fun hayesForUnix() {
        hayes.accept(unixConfigurator)
        assertEquals("&s1=4&D=3", hayes.configurationString)
    }

    @Test
    fun zoomForUnix() {
        zoom.accept(unixConfigurator)
        assertEquals(42, zoom.configurationValue)
    }

    @Test
    fun ernieForUnix() {
        ernie.accept(unixConfigurator)
        assertEquals("C is too slow", ernie.internalPattern)
    }
}
