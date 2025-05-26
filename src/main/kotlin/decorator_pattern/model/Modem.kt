package decorator_pattern.model

interface Modem {
    fun dial(pno: String)

    var speakerVolume: Int // 프로퍼티로 정의 (get/set)
    val phoneNumber: String? // 읽기 전용 프로퍼티, 다이얼 전에는 null일 수 있음
}
