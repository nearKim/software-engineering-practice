package extension_object.extension

import extension_object.XmlDocument
import extension_object.XmlElement

abstract class XmlPartExtension : PartExtension {
    // companion object를 사용하여 C#의 static XmlDocument 공유 개념 모방
    companion object {
        private val document = XmlDocument()
    }

    abstract val xmlElement: XmlElement

    protected fun newElement(name: String): XmlElement = document.createElement(name)

    protected fun newTextElement(
        name: String,
        text: String,
    ): XmlElement {
        val element = document.createElement(name)
        val xmlText = document.createTextNode(text)
        element.appendChild(xmlText)
        return element
    }
}
