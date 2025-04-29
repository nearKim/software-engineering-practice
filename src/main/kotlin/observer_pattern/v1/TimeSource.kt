package observer_pattern.v1

// Interface for the time source (e.g., Clock)
interface TimeSource {
    // FIXME: ClockDriver에 의존하고 있으므로 TimeSource를 "특정한 시계" 문맥을 제외한 곳에서 사용이 불가
    fun setDriver(driver: ClockDriver)
}
