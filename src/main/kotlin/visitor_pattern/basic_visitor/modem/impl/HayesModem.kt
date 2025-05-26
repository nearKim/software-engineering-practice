package visitor_pattern.basic_visitor.modem.impl

import visitor_pattern.basic_visitor.modem.Modem
import visitor_pattern.basic_visitor.modem.ModemVisitor

class HayesModem : Modem {
    // This property will be modified by the visitor
    var configurationString: String? = null

    override fun dial(pno: String) { // Concrete dial logic for Hayes
    }

    override fun hangup() { // Concrete hangup logic for Hayes
    }

    override fun send(c: Char) { // Concrete send logic for Hayes
    }

    override fun recv(): Char = 0.toChar() // Placeholder

    override fun accept(v: ModemVisitor) {
        v.visit(this) // Double dispatch: calls the correct method on the visitor
    }
}
