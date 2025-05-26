package visitor_pattern.bom_example.model

import visitor_pattern.bom_example.visitor.PartVisitor

interface Part {
    val partNumber: String
    val description: String

    fun accept(v: PartVisitor) // Visitor를 받아들이는 메소드
}
