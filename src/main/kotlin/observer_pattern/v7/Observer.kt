package observer_pattern.v7

/** Generic observer in the classic Observer pattern. */
fun interface Observer {
    // TODO: Called by the Subject when something has changed.
    fun update()
}
