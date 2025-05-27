package extension_object.model

import extension_object.extension.CsvPiecePartExtension
import extension_object.extension.XmlPiecePartExtension

class PiecePart(
    partNumber: String,
    description: String,
    val cost: Double,
) : Part() {
    // Part의 추상 프로퍼티 구현
    override val partNumber: String = partNumber
    override val description: String = description

    init {
        addExtension("CSV", CsvPiecePartExtension(this))
        addExtension("XML", XmlPiecePartExtension(this))
    }
}
