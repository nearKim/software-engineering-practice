package visitor_pattern.acyclic_visitor.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import visitor_pattern.acyclic_visitor.modem.impl.*

class ModemAcyclicVisitorTest { // Renamed test class slightly
    private lateinit var configurator: UnixModemConfigurator
    private lateinit var hayes: HayesModem
    private lateinit var zoom: ZoomModem
    private lateinit var ernie: ErnieModem

    @BeforeEach
    fun setUp() {
        configurator = UnixModemConfigurator()
        hayes = HayesModem()
        zoom = ZoomModem()
        ernie = ErnieModem()
    }

    @Test
    fun hayesForUnix() {
        hayes.accept(configurator) // Pass the concrete visitor
        assertEquals("&s1=4&D=3", hayes.configurationString)
    }

    @Test
    fun zoomForUnix() {
        zoom.accept(configurator)
        assertEquals(42, zoom.configurationValue)
    }

    @Test
    fun ernieForUnix() {
        ernie.accept(configurator)
        assertEquals("C is too slow", ernie.internalPattern)
    }
}
