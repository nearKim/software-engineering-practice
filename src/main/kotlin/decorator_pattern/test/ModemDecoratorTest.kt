package decorator_pattern.test

import decorator_pattern.LoudDialModem
import decorator_pattern.model.HayesModem
import decorator_pattern.model.Modem
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ModemDecoratorTest {
    @Test
    fun createHayes() {
        val m: Modem = HayesModem()
        assertNull(m.phoneNumber, "Initial phone number should be null.")
        assertEquals(0, m.speakerVolume, "Initial speaker volume should be 0.")

        m.dial("5551212")
        assertEquals("5551212", m.phoneNumber, "Phone number after dial.")
        // HayesModem의 dial은 볼륨을 직접 변경하지 않으므로, 이전 볼륨(0) 유지 또는 설정된 값.
        // 여기서는 dial 이전에 명시적으로 설정하지 않았으므로 0이거나,
        // 만약 dial 내부에서 기본 볼륨을 설정한다면 그 값이 됨.
        // C# 테스트에서는 dial 후 speakerVolume이 0으로 남아있음을 가정함.
        // 이는 HayesModem의 Dial이 speakerVolume을 바꾸지 않음을 의미.
        assertEquals(0, m.speakerVolume, "Speaker volume after dial should remain unchanged unless set.")

        m.speakerVolume = 10
        assertEquals(10, m.speakerVolume, "Speaker volume after setting.")
    }

    @Test
    fun loudDialModem() {
        val hayesModem: Modem = HayesModem()
        val loudModem: Modem = LoudDialModem(hayesModem) // HayesModem을 LoudDialModem으로 장식

        assertNull(loudModem.phoneNumber, "Initial phone number through decorator should be null.")
        // LoudDialModem 생성 시점에는 내부 모뎀(hayesModem)의 볼륨을 건드리지 않음.
        assertEquals(0, loudModem.speakerVolume, "Initial speaker volume through decorator.")

        loudModem.dial("5551212") // LoudDialModem의 Dial 호출

        // phoneNumber는 내부 모뎀으로 위임됨
        assertEquals("5551212", loudModem.phoneNumber, "Phone number after loud dial.")
        // LoudDialModem의 Dial 메소드가 speakerVolume을 10으로 설정함
        assertEquals(10, loudModem.speakerVolume, "Speaker volume after loud dial should be 10.")

        // Decorator를 통해 볼륨을 다시 변경하는 것도 테스트 가능
        loudModem.speakerVolume = 5
        assertEquals(5, loudModem.speakerVolume, "Speaker volume after explicit set on decorator.")
        // 내부 모뎀의 볼륨도 변경되었는지 확인 (구현에 따라)
        assertEquals(5, hayesModem.speakerVolume, "Underlying HayesModem volume should also be 5.")
    }
}
