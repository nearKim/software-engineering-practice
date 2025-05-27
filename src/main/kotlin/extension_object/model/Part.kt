package extension_object.model

import extension_object.extension.BadPartExtension
import extension_object.extension.PartExtension

abstract class Part {
    private val extensions = mutableMapOf<String, PartExtension>()

    abstract val partNumber: String
    abstract val description: String

    fun addExtension(
        extensionType: String,
        extension: PartExtension,
    ) {
        extensions[extensionType] = extension
    }

    fun getExtension(extensionType: String): PartExtension = extensions[extensionType] ?: BadPartExtension()
}
