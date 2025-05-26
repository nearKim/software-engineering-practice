package visitor_pattern.acyclic_visitor.modem.impl

import visitor_pattern.acyclic_visitor.modem.Modem
import visitor_pattern.acyclic_visitor.modem.ModemVisitor
import visitor_pattern.acyclic_visitor.modem.ZoomModemVisitor

class ZoomModem : Modem { // Assuming it implements Modem
    var configurationValue: Int = 0

    override fun dial(pno: String) { // Zoom dial logic
    }

    override fun hangup() { // Zoom hangup logic
    }

    override fun send(c: Char) { // Zoom send logic
    }

    override fun recv(): Char = 0.toChar() // Placeholder

    override fun accept(v: ModemVisitor) {
        if (v is ZoomModemVisitor) {
            v.visit(this) // v is smart-cast
        }
    }
}
