package state_pattern.state_pattern.state

import state_pattern.state_pattern.Turnstile

internal class LockedTurnstileState : TurnstileState {
    override fun coin(t: Turnstile) {
        // When a coin is inserted in the LOCKED state:
        t.setUnlocked() // Transition the turnstile to the UNLOCKED state
        t.unlockAction() // Perform the unlock action
    }

    override fun pass(t: Turnstile) {
        // When a user tries to pass in the LOCKED state (without paying):
        t.alarmAction() // Trigger the alarm action
        // State remains LOCKED
    }
}
