package visitor_pattern.basic_visitor.modem.impl

import visitor_pattern.basic_visitor.modem.Modem
import visitor_pattern.basic_visitor.modem.ModemVisitor

class ErnieModem : Modem { // Assuming it implements Modem
    // This property will be modified by the visitor
    var internalPattern: String? = null

    override fun dial(pno: String) { // Concrete dial logic for Ernie
    }

    override fun hangup() { // Concrete hangup logic for Ernie
    }

    override fun send(c: Char) { // Concrete send logic for Ernie
    }

    override fun recv(): Char = 0.toChar() // Placeholder

    override fun accept(v: ModemVisitor) {
        v.visit(this) // Double dispatch
    }
}
