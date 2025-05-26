package visitor_pattern.acyclic_visitor.modem.impl

import visitor_pattern.acyclic_visitor.modem.HayesModemVisitor
import visitor_pattern.acyclic_visitor.modem.Modem
import visitor_pattern.acyclic_visitor.modem.ModemVisitor

class HayesModem : Modem {
    var configurationString: String? = null

    override fun dial(pno: String) { // Hayes dial logic
    }

    override fun hangup() { // Hayes hangup logic
    }

    override fun send(c: Char) { // Hayes send logic
    }

    override fun recv(): Char = 0.toChar() // Placeholder

    override fun accept(v: ModemVisitor) {
        // Attempt to cast to the specific visitor interface
        if (v is HayesModemVisitor) {
            v.visit(this) // v is smart-cast to HayesModemVisitor
        }
        // If v is not a HayesModemVisitor, this modem type might not be visitable by it,
        // or other specific visitor interfaces could be checked here.
    }
}
