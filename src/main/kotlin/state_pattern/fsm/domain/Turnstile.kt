package state_pattern.fsm.domain

class Turnstile(
    private val turnstileController: TurnstileController,
) {
    // 'internal' allows access within the same module, which typically includes tests.
    // If tests are in a different module and need to set state, this might need adjustment
    // or tests should rely on initial state + handleEvent calls.
    // The C# tests directly set this state.
    internal var state: State = State.LOCKED

    fun handleEvent(event: Event) {
        when (state) {
            State.LOCKED -> {
                when (event) {
                    Event.COIN -> {
                        state = State.UNLOCKED
                        turnstileController.unlock()
                    }

                    Event.PASS -> {
                        turnstileController.alarm()
                    }
                }
            }

            State.UNLOCKED -> {
                when (event) {
                    Event.COIN -> {
                        turnstileController.thankyou()
                    }

                    Event.PASS -> {
                        state = State.LOCKED
                        turnstileController.lock()
                    }
                }
            }
        }
    }
}
