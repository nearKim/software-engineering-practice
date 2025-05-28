package state_pattern.fsm.domain

enum class State {
    LOCKED,
    UNLOCKED,
}

enum class Event {
    COIN,
    PASS,
}

/**
 * Interface for actions the Turnstile can trigger.
 */
interface TurnstileController {
    fun lock()

    fun unlock()

    fun thankyou() // Consistent with C# Thankyou()

    fun alarm()
}
