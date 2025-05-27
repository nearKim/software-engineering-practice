package extension_object.extension

import extension_object.XmlElement
import extension_object.model.Assembly

class XmlAssemblyExtension(
    private val assembly: Assembly,
) : XmlPartExtension() {
    override val xmlElement: XmlElement
        get() {
            val e = newElement("Assembly")
            e.appendChild(newTextElement("PartNumber", assembly.partNumber))
            e.appendChild(newTextElement("Description", assembly.description))

            val partsElement = newElement("Parts")
            assembly.parts.forEach { part ->
                val xpe = part.getExtension("XML") as? XmlPartExtension
                xpe?.let { partsElement.appendChild(it.xmlElement) }
            }
            e.appendChild(partsElement)
            return e
        }
}
