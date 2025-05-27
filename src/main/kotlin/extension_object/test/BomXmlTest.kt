package extension_object.test

import extension_object.XmlElement
import extension_object.XmlNodeList
import extension_object.extension.BadPartExtension
import extension_object.extension.CsvPartExtension
import extension_object.extension.XmlPartExtension
import extension_object.model.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BomXmlTest {
    private lateinit var p1: PiecePart
    private lateinit var p2: PiecePart
    private lateinit var a: Assembly

    @BeforeEach
    fun setUp() {
        p1 = PiecePart("997624", "MyPart", 3.20)
        p2 = PiecePart("7734", "Hell", 666.0)
        a = Assembly("5879", "MyAssembly")
    }

    @Test
    fun createPart() {
        assertEquals("997624", p1.partNumber)
        assertEquals("MyPart", p1.description)
        assertEquals(3.20, p1.cost, 0.01)
    }

    @Test
    fun createAssembly() {
        assertEquals("5879", a.partNumber)
        assertEquals("MyAssembly", a.description)
    }

    @Test
    fun assemblyContents() { // C#의 Assembly 테스트와 동일한 이름 피하기 위해 변경
        a.add(p1)
        a.add(p2)
        assertEquals(2, a.parts.size)
        assertEquals(p1, a.parts[0])
        assertEquals(p2, a.parts[1])
    }

    @Test
    fun assemblyOfAssemblies() {
        val subAssembly = Assembly("1324", "SubAssembly")
        subAssembly.add(p1)
        a.add(subAssembly)
        assertEquals(subAssembly, a.parts[0])
    }

    // XML Helper methods
    private fun childText(
        element: XmlElement,
        childName: String,
    ): String = child(element, childName)?.innerText ?: ""

    private fun child(
        element: XmlElement,
        childName: String,
    ): XmlElement? {
        // 플레이스홀더 XmlElement는 GetElementsByTagName이 직계자식만 반환한다고 가정하지 않음.
        // C#의 GetElementsByTagName은 모든 하위 노드를 검색.
        // 여기서는 첫번째 매칭되는 자식 혹은 그 하위 자식을 찾는다고 가정.
        // 더 정확한 DOM 탐색을 위해서는 플레이스홀더 XML 클래스 개선 필요.
        // 현재 플레이스홀더는 이름으로 첫번째 자식만 찾는다고 가정하고 단순화 할 수도 있음.
        // C#의 GetElementsByTagName은 해당 이름의 모든 요소를 반환, Item(0)은 첫번째.
        // 여기서는 플레이스홀더 XmlElement의 getElementsByTagName이 List<XmlNode>를 반환하고, 첫번째를 가져옴
        val children: XmlNodeList = element.getElementsByTagName(childName)
        return children.item(0) as? XmlElement
    }

    @Test
    fun piecePart1XML() {
        val e = p1.getExtension("XML")
        val xe = e as? XmlPartExtension
        assertNotNull(xe)
        val xml = xe!!.xmlElement
        assertEquals("PiecePart", xml.name)
        assertEquals("997624", childText(xml, "PartNumber"))
        assertEquals("MyPart", childText(xml, "Description"))
        assertEquals(3.2, childText(xml, "Cost").toDouble(), 0.01)
    }

    @Test
    fun piecePart2XML() {
        val e = p2.getExtension("XML")
        val xe = e as? XmlPartExtension
        assertNotNull(xe)
        val xml = xe!!.xmlElement
        assertEquals("PiecePart", xml.name)
        assertEquals("7734", childText(xml, "PartNumber"))
        assertEquals("Hell", childText(xml, "Description"))
        assertEquals(666.0, childText(xml, "Cost").toDouble(), 0.01)
    }

    @Test
    fun simpleAssemblyXML() {
        val e = a.getExtension("XML")
        val xe = e as? XmlPartExtension
        assertNotNull(xe)
        val xml = xe!!.xmlElement
        assertEquals("Assembly", xml.name)
        assertEquals("5879", childText(xml, "PartNumber"))
        assertEquals("MyAssembly", childText(xml, "Description"))

        val parts = child(xml, "Parts")
        assertNotNull(parts)
        // 현재 플레이스홀더 XmlElement의 childNodes는 직접 추가된 자식만 포함
        assertEquals(0, parts!!.childNodes.size)
    }

    @Test
    fun assemblyWithPartsXML() {
        a.add(p1)
        a.add(p2)
        val e = a.getExtension("XML")
        val xe = e as? XmlPartExtension
        assertNotNull(xe)
        val xml = xe!!.xmlElement
        assertEquals("Assembly", xml.name)
        assertEquals("5879", childText(xml, "PartNumber"))
        assertEquals("MyAssembly", childText(xml, "Description"))

        val parts = child(xml, "Parts")
        assertNotNull(parts)
        assertEquals(2, parts!!.childNodes.size) // XmlAssemblyExtension이 자식들을 partsElement에 추가

        val partElement1 = parts.childNodes[0] as? XmlElement
        assertNotNull(partElement1)
        assertEquals("PiecePart", partElement1!!.name)
        assertEquals("997624", childText(partElement1, "PartNumber"))

        val partElement2 = parts.childNodes[1] as? XmlElement
        assertNotNull(partElement2)
        assertEquals("PiecePart", partElement2!!.name)
        assertEquals("7734", childText(partElement2, "PartNumber"))
    }

    @Test
    fun piecePart1toCSV() {
        val e = p1.getExtension("CSV")
        val ce = e as? CsvPartExtension
        assertNotNull(ce)
        val csv = ce!!.csvText
        assertEquals("PiecePart,997624,MyPart,3.2", csv)
    }

    @Test
    fun piecePart2toCSV() {
        val e = p2.getExtension("CSV")
        val ce = e as? CsvPartExtension
        assertNotNull(ce)
        val csv = ce!!.csvText
        assertEquals("PiecePart,7734,Hell,666.0", csv) // .0 for double
    }

    @Test
    fun simpleAssemblyCSV() {
        val e = a.getExtension("CSV")
        val ce = e as? CsvPartExtension
        assertNotNull(ce)
        val csv = ce!!.csvText
        assertEquals("Assembly,5879,MyAssembly", csv)
    }

    @Test
    fun assemblyWithPartsCSV() {
        a.add(p1)
        a.add(p2)
        val e = a.getExtension("CSV")
        val ce = e as? CsvPartExtension
        assertNotNull(ce)
        val csv = ce!!.csvText
        assertEquals(
            "Assembly,5879,MyAssembly,{PiecePart,997624,MyPart,3.2},{PiecePart,7734,Hell,666.0}",
            csv,
        )
    }

    @Test
    fun badExtension() {
        val pe = p1.getExtension("ThisStringDoesn'tMatchAnyException") // Extension 오타 수정
        assertTrue(pe is BadPartExtension)
    }
}
