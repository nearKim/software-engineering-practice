package visitor_pattern.acyclic_visitor.modem.impl

import visitor_pattern.acyclic_visitor.modem.ErnieModemVisitor
import visitor_pattern.acyclic_visitor.modem.Modem
import visitor_pattern.acyclic_visitor.modem.ModemVisitor

class ErnieModem : Modem { // Assuming it implements Modem
    var internalPattern: String? = null

    override fun dial(pno: String) { // Ernie dial logic
    }

    override fun hangup() { // Ernie hangup logic
    }

    override fun send(c: Char) { // Ernie send logic
    }

    override fun recv(): Char = 0.toChar() // Placeholder

    override fun accept(v: ModemVisitor) {
        if (v is ErnieModemVisitor) {
            v.visit(this) // v is smart-cast
        }
    }
}
