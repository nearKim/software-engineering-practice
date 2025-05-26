package visitor_pattern.basic_visitor.modem

/**
 * The Element interface declares an `accept` method that takes the base
 * visitor interface as an argument.
 */
interface Modem {
    fun dial(pno: String)

    fun hangup()

    fun send(c: Char)

    fun recv(): Char

    fun accept(v: ModemVisitor) // Core of the Visitor pattern
}