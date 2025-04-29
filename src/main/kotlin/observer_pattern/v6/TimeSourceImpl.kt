package observer_pattern.v6

class TimeSourceImpl : TimeSource {
    private val observers = mutableListOf<ClockObserver>()

    fun notify(
        hours: Int,
        mins: Int,
        secs: Int,
    ) {
        // FIXME: 관찰대상(Subject)인 TimeSourceImpl이, 등록된 observer들에게 update 메소드 argument로 "시간 data"를 전달 (push-model)
        // FIXME: 이에 따라 관찰대상인 TimeSourceImpl이 반드시 "시간"이라는 context에 묶이므로, 네이밍에 Time이 들어가야 함
        observers.forEach { it.update(hours, mins, secs) }
    }

    override fun registerObserver(observer: ClockObserver) {
        observers += observer
    }
}
