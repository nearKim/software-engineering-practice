package extension_object.model

import extension_object.extension.CsvAssemblyExtension
import extension_object.extension.XmlAssemblyExtension

class Assembly(
    partNumber: String,
    description: String,
) : Part() {
    private val _parts = mutableListOf<Part>()

    val parts: List<Part>
        get() = _parts.toList()

    // Part의 추상 프로퍼티 구현
    override val partNumber: String = partNumber
    override val description: String = description

    init {
        addExtension("CSV", CsvAssemblyExtension(this))
        addExtension("XML", XmlAssemblyExtension(this))
    }

    fun add(part: Part) {
        _parts.add(part)
    }
}
