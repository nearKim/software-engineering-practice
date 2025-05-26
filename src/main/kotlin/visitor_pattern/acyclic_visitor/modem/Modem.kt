package visitor_pattern.acyclic_visitor.modem

import visitor_pattern.acyclic_visitor.modem.impl.ErnieModem
import visitor_pattern.acyclic_visitor.modem.impl.HayesModem
import visitor_pattern.acyclic_visitor.modem.impl.ZoomModem

// Forward declarations for clarity, though often managed by package structure
interface ModemVisitor // Degenerate base visitor interface (no methods)

interface HayesModemVisitor : ModemVisitor {
    fun visit(modem: HayesModem)
}

interface ZoomModemVisitor : ModemVisitor {
    fun visit(modem: ZoomModem)
}

interface ErnieModemVisitor : ModemVisitor {
    fun visit(modem: ErnieModem)
}

/**
 * The Modem interface (Visitable Element).
 * Its Accept method takes the degenerate ModemVisitor.
 */
interface Modem {
    fun dial(pno: String)

    fun hangup()

    fun send(c: Char)

    fun recv(): Char

    fun accept(v: ModemVisitor)
}
