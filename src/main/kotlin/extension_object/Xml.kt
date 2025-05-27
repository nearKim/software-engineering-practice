package extension_object

// System.Xml.XmlDocument, XmlElement 등의 최소 기능만 모방하는 플레이스홀더
class XmlDocument {
    fun createElement(name: String): XmlElement = XmlElement(name)

    fun createTextNode(text: String): XmlText = XmlText(text)
}

open class XmlNode(
    val name: String,
) {
    val childNodes = mutableListOf<XmlNode>()
    open var innerText: String = ""

    fun appendChild(node: XmlNode) {
        childNodes.add(node)
        if (node is XmlText) { // 단순화를 위해 text 노드 추가 시 부모의 innerText 업데이트
            innerText += node.textValue
        }
    }
}

class XmlElement(
    name: String,
) : XmlNode(name) {
    fun getElementsByTagName(tagName: String): XmlNodeList {
        val list = mutableListOf<XmlNode>()

        fun findRecursive(node: XmlNode) {
            if (node.name == tagName) {
                list.add(node)
            }
            node.childNodes.forEach { findRecursive(it) }
        }
        childNodes.forEach { findRecursive(it) } // 현재 요소의 자식들부터 검색 시작
        return XmlNodeList(list)
    }
}

class XmlText(
    val textValue: String,
) : XmlNode("#text") { // 텍스트 노드는 이름이 중요하지 않음
    override var innerText: String
        get() = textValue
        set(_) {} // 텍스트 노드의 값은 불변으로 간주
}

class XmlNodeList(
    private val nodes: List<XmlNode>,
) {
    val count: Int get() = nodes.size

    fun item(index: Int): XmlNode? = nodes.getOrNull(index)
}
