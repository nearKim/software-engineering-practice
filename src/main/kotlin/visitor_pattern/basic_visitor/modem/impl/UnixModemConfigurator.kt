package visitor_pattern.basic_visitor.modem.impl

import visitor_pattern.basic_visitor.modem.ModemVisitor

/**
 * Concrete Visitor that implements OS-specific configuration for each modem type.
 */
class UnixModemConfigurator : ModemVisitor {
    override fun visit(modem: HayesModem) {
        // Hayes-specific UNIX configuration
        modem.configurationString = "&s1=4&D=3"
        println("Configured HayesModem for UNIX: ${modem.configurationString}")
    }

    override fun visit(modem: ZoomModem) {
        // Zoom-specific UNIX configuration
        modem.configurationValue = 42
        println("Configured ZoomModem for UNIX: ${modem.configurationValue}")
    }

    override fun visit(modem: ErnieModem) {
        // Ernie-specific UNIX configuration
        modem.internalPattern = "C is too slow"
        println("Configured ErnieModem for UNIX: ${modem.internalPattern}")
    }
}
