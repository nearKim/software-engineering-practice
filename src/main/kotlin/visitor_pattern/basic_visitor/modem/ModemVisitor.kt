package visitor_pattern.basic_visitor.modem

import visitor_pattern.basic_visitor.modem.impl.ErnieModem
import visitor_pattern.basic_visitor.modem.impl.HayesModem
import visitor_pattern.basic_visitor.modem.impl.ZoomModem

/**
 * The Visitor interface declares a set of visiting methods that correspond
 * to concrete classes of modem elements.
 */
interface ModemVisitor {
    fun visit(modem: HayesModem)

    fun visit(modem: ZoomModem)

    fun visit(modem: ErnieModem)
    // If new modem types are added, a new visit method might be needed here,
    // which is a characteristic of the classic Visitor pattern.
}