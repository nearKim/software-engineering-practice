package visitor_pattern.bom_example.visitor

import visitor_pattern.bom_example.model.Assembly
import visitor_pattern.bom_example.model.PiecePart

/**
 * Part 계층 구조를 방문하는 Visitor의 기본 인터페이스
 */
interface PartVisitor {
    fun visit(pp: PiecePart)

    fun visit(a: Assembly)
}
