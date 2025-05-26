package visitor_pattern.bom_example.visitor

import visitor_pattern.bom_example.model.Assembly
import visitor_pattern.bom_example.model.PiecePart

/**
 * BOM 내의 개별 부품 수 및 각 부품 번호별 개수를 계산하는 Visitor
 */
class PartCountVisitor : PartVisitor {
    var pieceCount: Int = 0
        private set
    private val pieceMap = mutableMapOf<String, Int>()

    override fun visit(p: PiecePart) {
        pieceCount++ // 전체 개별 부품 수 증가
        val partNumber = p.partNumber
        val currentCount = pieceMap.getOrDefault(partNumber, 0)
        pieceMap[partNumber] = currentCount + 1 // 해당 부품 번호의 개수 증가
    }

    override fun visit(a: Assembly) {
        // Assembly 방문 시에는 특별한 카운팅 로직 없음
    }

    val partNumberCount: Int // 고유한 부품 번호의 종류 수
        get() = pieceMap.size

    fun getCountForPart(partNumber: String): Int = pieceMap.getOrDefault(partNumber, 0)
}
