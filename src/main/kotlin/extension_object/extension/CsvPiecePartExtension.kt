package extension_object.extension

import extension_object.model.PiecePart

class CsvPiecePartExtension(
    private val piecePart: PiecePart,
) : CsvPartExtension {
    override val csvText: String
        get() {
            return StringBuilder("PiecePart,")
                .apply {
                    append(piecePart.partNumber)
                    append(",")
                    append(piecePart.description)
                    append(",")
                    append(piecePart.cost)
                }.toString()
        }
}
