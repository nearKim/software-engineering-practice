package visitor_pattern.bom_example.visitor

import visitor_pattern.bom_example.model.Assembly
import visitor_pattern.bom_example.model.PiecePart

/**
 * BOM의 총 비용을 계산하는 Visitor
 */
class ExplodedCostVisitor : PartVisitor {
    var cost: Double = 0.0
        private set // 외부에서는 읽기만 가능하도록 설정

    override fun visit(p: PiecePart) {
        cost += p.cost // 개별 부품의 비용을 누적
    }

    override fun visit(a: Assembly) {
        // Assembly 자체는 비용을 가지지 않으므로 이 Visitor에서는 특별한 처리를 하지 않음
        // 비용은 PiecePart 방문 시 누적됨
    }
}
