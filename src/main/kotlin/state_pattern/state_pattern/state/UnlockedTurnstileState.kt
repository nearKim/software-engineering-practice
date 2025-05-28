package state_pattern.state_pattern.state

import state_pattern.state_pattern.Turnstile

internal class UnlockedTurnstileState : TurnstileState {
    override fun coin(t: Turnstile) {
        // When a coin is inserted in the UNLOCKED state (e.g., an extra coin):
        t.thankyouAction() // Perform the "thank you" action (e.g., return coin, display message)
        // State remains UNLOCKED
    }

    override fun pass(t: Turnstile) {
        // When a user passes through in the UNLOCKED state:
        t.setLocked() // Transition the turnstile back to the LOCKED state
        t.lockAction() // Perform the lock action
    }
}
