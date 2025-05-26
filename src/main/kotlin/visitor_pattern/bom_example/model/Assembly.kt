package visitor_pattern.bom_example.model

import visitor_pattern.bom_example.visitor.PartVisitor

/**
 * 여러 부품(또는 다른 조립품)으로 구성된 조립품을 나타내는 클래스 (Composite Element)
 */
class Assembly(
    override val partNumber: String,
    override val description: String,
) : Part {
    private val _parts = mutableListOf<Part>() // 내부적으로 관리되는 부품 리스트

    val parts: List<Part> // 외부에는 읽기 전용 리스트로 노출
        get() = _parts.toList()

    fun add(part: Part) {
        _parts.add(part)
    }

    override fun accept(v: PartVisitor) {
        v.visit(this) // Assembly 자신을 방문
        _parts.forEach { part ->
            // 포함된 모든 부품/조립품에 대해 accept 호출 (재귀적 순회)
            part.accept(v)
        }
    }
}
