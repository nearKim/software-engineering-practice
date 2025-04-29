package observer_pattern.v7

/**
 * Observable side of the Observer pattern (pushes *notifications* only).
 * Pull-model이 적용된 Subject.
 */
open class Subject {
    private val observers = mutableListOf<Observer>()

    fun registerObserver(observer: Observer) {
        observers += observer
    }

    protected fun notifyObservers() {
        // TODO: pull-model 에서는 notify, update에 아무것도 전달하지 않는다. 그러므로 네이밍도 중립적인 Subject 유지가능
        observers.forEach { it.update() }
    }
}
