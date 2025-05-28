package state_pattern.state_pattern.state

import state_pattern.state_pattern.Turnstile

/**
 * Defines the interface for all concrete state classes.
 * It declares methods for each event the FSM can handle.
 * The context (Turnstile) is passed to allow states to trigger actions
 * or state changes on the context.
 */
interface TurnstileState {
    fun coin(t: Turnstile) // Event: Coin inserted

    fun pass(t: Turnstile) // Event: User passes through
}
