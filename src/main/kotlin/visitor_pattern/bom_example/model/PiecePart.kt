package visitor_pattern.bom_example.model

import visitor_pattern.bom_example.visitor.PartVisitor

/**
 * 개별 부품을 나타내는 클래스 (Leaf Element)
 */
class PiecePart(
    override val partNumber: String,
    override val description: String,
    val cost: Double,
) : Part {
    override fun accept(v: PartVisitor) {
        v.visit(this) // PiecePart에 대한 visit 메소드 호출
    }
}
