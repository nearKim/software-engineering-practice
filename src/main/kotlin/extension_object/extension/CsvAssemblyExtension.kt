package extension_object.extension

import extension_object.model.Assembly

class CsvAssemblyExtension(
    private val assembly: Assembly,
) : CsvPartExtension {
    override val csvText: String
        get() {
            return StringBuilder("Assembly,")
                .apply {
                    append(assembly.partNumber)
                    append(",")
                    append(assembly.description)
                    assembly.parts.forEach { part ->
                        val cpe = part.getExtension("CSV") as? CsvPartExtension
                        cpe?.let {
                            append(",{")
                            append(it.csvText)
                            append("}")
                        }
                    }
                }.toString()
        }
}
