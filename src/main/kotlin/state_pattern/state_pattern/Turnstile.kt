package state_pattern.state_pattern

import state_pattern.state_pattern.state.*

class Turnstile(
    private val turnstileController: TurnstileController,
) {
    companion object {
        // Static-like state instances, accessible within the module
        internal val lockedState: TurnstileState = LockedTurnstileState()
        internal val unlockedState: TurnstileState = UnlockedTurnstileState()
    }

    // Current state of the turnstile, initialized to unlockedState as per C# code.
    // 'internal' makes it accessible within the same module (e.g., for tests or state classes if needed directly).
    // The state objects themselves call public/internal methods on Turnstile to change this.
    internal var currentState: TurnstileState = unlockedState
        private set // State should ideally be changed only via setLocked/setUnlocked

    // Public event methods - these are the primary interface for interacting with the FSM
    fun coin() {
        currentState.coin(this) // Delegate event to the current state object
    }

    fun pass() {
        currentState.pass(this) // Delegate event to the current state object
    }

    // Methods for state objects to change the Turnstile's state
    // Marked internal as they are primarily for state objects within the same module.
    internal fun setLocked() {
        currentState = lockedState
        println("Turnstile: State changed to LOCKED") // Optional: for debugging/logging
    }

    internal fun setUnlocked() {
        currentState = unlockedState
        println("Turnstile: State changed to UNLOCKED") // Optional: for debugging/logging
    }

    // Public query methods
    fun isLocked(): Boolean = currentState == lockedState

    fun isUnlocked(): Boolean = currentState == unlockedState

    // Internal action methods that state objects call.
    // These methods proxy the call to the injected TurnstileController.
    // They are 'internal' so state objects (if in the same module/package) can call them.
    internal fun thankyouAction() {
        turnstileController.thankyou()
    }

    internal fun alarmAction() {
        turnstileController.alarm()
    }

    internal fun lockAction() {
        turnstileController.lock()
    }

    internal fun unlockAction() {
        turnstileController.unlock()
    }
}
