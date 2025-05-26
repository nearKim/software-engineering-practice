package visitor_pattern.basic_visitor.modem.impl

import visitor_pattern.basic_visitor.modem.Modem
import visitor_pattern.basic_visitor.modem.ModemVisitor

class ZoomModem : Modem { // Assuming it implements Modem
    // This property will be modified by the visitor
    var configurationValue: Int = 0

    override fun dial(pno: String) { // Concrete dial logic for Zoom
    }

    override fun hangup() { // Concrete hangup logic for Zoom
    }

    override fun send(c: Char) { // Concrete send logic for Zoom
    }

    override fun recv(): Char = 0.toChar() // Placeholder

    override fun accept(v: ModemVisitor) {
        v.visit(this) // Double dispatch
    }
}