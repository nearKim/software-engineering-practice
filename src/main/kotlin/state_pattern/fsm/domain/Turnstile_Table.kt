package state_pattern.fsm.domain

class Turnstile_Table(
    private val controller: TurnstileController,
) {
    // 현재 Turnstile의 상태. 'internal'로 선언하여 모듈 내에서 접근 가능 (테스트 등).
    internal var currentState: State = State.LOCKED
        private set // 외부에서는 읽기만 가능하도록 setter를 private으로 제한 (선택 사항)

    // Transition Table: 상태 전이 규칙들을 저장하는 리스트
    // 각 Transition 객체는 (시작 상태, 트리거 이벤트, 종료 상태, 수행 액션)을 정의한다.
    private val transitions = mutableListOf<Transition>()

    // C#의 private delegate void Action()은 Kotlin의 함수 타입 () -> Unit으로 대체된다.
    // 별도의 delegate 선언은 필요 없다.

    init {
        // 액션 정의: controller의 메소드를 호출하는 람다 함수들
        val unlockAction: () -> Unit = { controller.unlock() }
        val alarmAction: () -> Unit = { controller.alarm() }
        val thankYouAction: () -> Unit = { controller.thankyou() }
        val lockAction: () -> Unit = { controller.lock() }

        // --- Transition Table Population ---
        // 상태 전이 테이블을 초기화한다.
        // 각 addTransition 호출은 FSM의 한 가지 규칙을 정의한다.
        addTransition(State.LOCKED, Event.COIN, State.UNLOCKED, unlockAction)
        addTransition(State.LOCKED, Event.PASS, State.LOCKED, alarmAction)
        addTransition(State.UNLOCKED, Event.COIN, State.UNLOCKED, thankYouAction)
        addTransition(State.UNLOCKED, Event.PASS, State.LOCKED, lockAction)
        // --- End of Transition Table Population ---
    }

    /**
     * 새로운 상태 전이 규칙을 테이블에 추가하는 private 헬퍼 함수.
     */
    private fun addTransition(
        startState: State,
        triggerEvent: Event,
        endState: State,
        actionToPerform: () -> Unit,
    ) {
        transitions.add(Transition(startState, triggerEvent, endState, actionToPerform))
    }

    /**
     * --- FSM Engine ---
     * 외부 이벤트를 받아 처리하는 상태 머신의 핵심 로직 (엔진).
     * 현재 상태와 발생한 이벤트를 기반으로 전이 테이블을 검색하여
     * 적절한 상태 변경 및 액션 수행을 담당한다.
     */
    fun handleEvent(event: Event) {
        // 전이 테이블에 정의된 모든 전이 규칙을 순회한다.
        for (transition in transitions) {
            // 현재 상태(currentState)와 발생한 이벤트(event)가
            // 해당 전이 규칙의 시작 상태(startState) 및 트리거 이벤트(trigger)와 일치하는지 확인한다.
            if (currentState == transition.startState && event == transition.trigger) {
                currentState = transition.endState // 상태를 새로운 상태로 변경한다.
                transition.action() // 정의된 액션을 수행한다.
                // 중요: 원본 C# 코드와 동일하게, 여기서 'break'나 'return'이 없다.
                // 이는 하나의 이벤트에 대해 여러 전이가 조건(현재 상태 변경 포함)을 만족하면
                // 모두 실행될 수 있음을 의미한다. 일반적인 FSM에서는 보통 첫 번째 매칭되는
                // 전이만 처리하므로, 그러한 동작을 원한다면 이 지점에서 'return'을 추가해야 한다.
                // 이 코드는 C#의 동작을 그대로 번역한 것이다.
            }
        }
        // 만약 일치하는 전이가 없다면, 현재 상태는 변경되지 않고 아무 액션도 수행되지 않는다.
    }

    /**
     * 상태 전이의 세부 정보를 저장하는 private 데이터 클래스.
     */
    private data class Transition(
        val startState: State,
        val trigger: Event,
        val endState: State,
        val action: () -> Unit, // 수행될 액션을 나타내는 함수 타입
    )
}
