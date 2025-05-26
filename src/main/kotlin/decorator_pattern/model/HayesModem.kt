package decorator_pattern.model

class HayesModem : Modem {
    private var _phoneNumber: String? = null
    private var _speakerVolume: Int = 0

    override fun dial(pno: String) {
        _phoneNumber = pno
        // 실제 Hayes 모뎀 다이얼 로직
        println("HayesModem: Dialing $pno with volume $_speakerVolume")
    }

    override var speakerVolume: Int
        get() = _speakerVolume
        set(value) {
            _speakerVolume = value
            println("HayesModem: Speaker volume set to $value")
        }

    override val phoneNumber: String?
        get() = _phoneNumber
}
