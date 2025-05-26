package decorator_pattern

import decorator_pattern.decorator.ModemDecorator
import decorator_pattern.model.Modem

/**
 * 큰 소리로 다이얼하는 기능을 추가하는 구체적인 Decorator.
 */
class LoudDialModem(
    modem: Modem,
) : ModemDecorator(modem) {
    override fun dial(pno: String) {
        println("LoudDialModem: Preparing for loud dial...")
        wrappedModem.speakerVolume = 10 // 원하는 볼륨 값 (예제에서는 10 또는 11)
        // C# 예제에서는 10을 사용했으므로 10으로 설정.
        // 텍스트에서는 11 ("it's one more than 10, isn't it?")을 언급하기도 함.
        println("LoudDialModem: Volume set to ${wrappedModem.speakerVolume} before dialing.")
        super.dial(pno) // ModemDecorator를 통해 wrappedModem의 dial 호출
        // 또는 wrappedModem.dial(pno) 직접 호출도 가능 (의미상 동일)
        println("LoudDialModem: Dialing completed for $pno.")
    }

    // speakerVolume 및 phoneNumber 프로퍼티는 ModemDecorator에서 by wrappedModem을 통해
    // 이미 위임되었으므로 LoudDialModem에서 별도로 오버라이드할 필요가 없음.
    // 만약 LoudDialModem이 speakerVolume을 다르게 보고하거나 제어해야 한다면 오버라이드 가능.
    // 예: override var speakerVolume: Int
    //          get() = wrappedModem.speakerVolume
    //          set(value) { wrappedModem.speakerVolume = value /* + 추가 로직 */ }
}
