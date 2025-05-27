package extension_object.extension

import extension_object.XmlElement
import extension_object.model.PiecePart

class XmlPiecePartExtension(
    private val piecePart: PiecePart,
) : XmlPartExtension() {
    override val xmlElement: XmlElement
        get() {
            val e = newElement("PiecePart")
            e.appendChild(newTextElement("PartNumber", piecePart.partNumber))
            e.appendChild(newTextElement("Description", piecePart.description))
            e.appendChild(newTextElement("Cost", piecePart.cost.toString()))
            return e
        }
}
