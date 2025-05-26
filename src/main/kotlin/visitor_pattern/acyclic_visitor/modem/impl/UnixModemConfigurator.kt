package visitor_pattern.acyclic_visitor.modem.impl

import visitor_pattern.acyclic_visitor.modem.*

/**
 * Concrete Visitor that implements specific visitor interfaces for each modem type
 * it knows how to configure.
 */
class UnixModemConfigurator :
    HayesModemVisitor,
    ZoomModemVisitor,
    ErnieModemVisitor {
    // No need to extend the degenerate ModemVisitor if interfaces already do,
    // but could be `ModemVisitor, HayesModemVisitor...` if ModemVisitor had common state/default methods.
    // Here, ModemVisitor is just a marker.

    override fun visit(modem: HayesModem) {
        modem.configurationString = "&s1=4&D=3"
        println("ACYCLIC: Configured HayesModem for UNIX: ${modem.configurationString}")
    }

    override fun visit(modem: ZoomModem) {
        modem.configurationValue = 42
        println("ACYCLIC: Configured ZoomModem for UNIX: ${modem.configurationValue}")
    }

    override fun visit(modem: ErnieModem) {
        modem.internalPattern = "C is too slow"
        println("ACYCLIC: Configured ErnieModem for UNIX: ${modem.internalPattern}")
    }
}
