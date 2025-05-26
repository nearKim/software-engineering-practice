package decorator_pattern.decorator

import decorator_pattern.model.Modem

/**
 * 기본 Modem Decorator 클래스.
 * Modem 인터페이스를 구현하며, 감싸고 있는 Modem 객체에 대부분의 호출을 위임한다.
 * Kotlin의 인터페이스 위임(by)을 사용하여 간결하게 구현.
 */
open class ModemDecorator(
    protected val wrappedModem: Modem,
) : Modem by wrappedModem
