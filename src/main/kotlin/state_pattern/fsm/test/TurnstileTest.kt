package state_pattern.fsm.test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test // Ensure this is from JUnit 5 (org.junit.jupiter.api)
import state_pattern.fsm.domain.*

class TurnstileTest {
    private lateinit var turnstile: Turnstile
    private lateinit var controllerSpoof: TurnstileControllerSpoof

    /**
     * A "spoof" or "mock" implementation of TurnstileController for testing.
     * It records whether its methods have been called.
     */
    private class TurnstileControllerSpoof : TurnstileController {
        var lockCalled: Boolean = false
        var unlockCalled: Boolean = false
        var thankyouCalled: Boolean = false
        var alarmCalled: Boolean = false

        override fun lock() {
            lockCalled = true
        }

        override fun unlock() {
            unlockCalled = true
        }

        override fun thankyou() {
            thankyouCalled = true
        }

        override fun alarm() {
            alarmCalled = true
        }
    }

    @BeforeEach
    fun setUp() {
        controllerSpoof = TurnstileControllerSpoof()
        turnstile = Turnstile(controllerSpoof)
    }

    @Test
    fun initialConditions() {
        assertEquals(State.LOCKED, turnstile.state, "Turnstile should be locked initially.")
    }

    @Test
    fun coinInLockedState() {
        turnstile.state = State.LOCKED // Set up precondition
        turnstile.handleEvent(Event.COIN)
        assertEquals(State.UNLOCKED, turnstile.state, "Should be unlocked after coin in locked state.")
        assertTrue(controllerSpoof.unlockCalled, "Unlock should have been called.")
    }

    @Test
    fun coinInUnlockedState() {
        turnstile.state = State.UNLOCKED // Set up precondition
        turnstile.handleEvent(Event.COIN)
        assertEquals(State.UNLOCKED, turnstile.state, "Should remain unlocked after coin in unlocked state.")
        assertTrue(controllerSpoof.thankyouCalled, "Thankyou should have been called.")
    }

    @Test
    fun passInLockedState() {
        turnstile.state = State.LOCKED // Set up precondition
        turnstile.handleEvent(Event.PASS)
        assertEquals(State.LOCKED, turnstile.state, "Should remain locked after pass in locked state.")
        assertTrue(controllerSpoof.alarmCalled, "Alarm should have been called.")
    }

    @Test
    fun passInUnlockedState() {
        turnstile.state = State.UNLOCKED // Set up precondition
        turnstile.handleEvent(Event.PASS)
        assertEquals(State.LOCKED, turnstile.state, "Should be locked after pass in unlocked state.")
        assertTrue(controllerSpoof.lockCalled, "Lock should have been called.")
    }
}
