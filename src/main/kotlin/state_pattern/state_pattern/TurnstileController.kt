package state_pattern.state_pattern

interface TurnstileController {
    fun lock()

    fun unlock()

    fun thankyou()

    fun alarm()
}
