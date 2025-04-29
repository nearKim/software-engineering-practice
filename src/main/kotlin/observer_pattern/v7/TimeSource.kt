package observer_pattern.v7

/** Pull-model accessor for time. */
interface TimeSource {
    fun hours(): Int

    fun minutes(): Int

    fun seconds(): Int
}
