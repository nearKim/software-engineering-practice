package visitor_pattern.bom_example.test

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import visitor_pattern.bom_example.model.*
import visitor_pattern.bom_example.visitor.*

class BOMReportTest {
    private lateinit var p1: PiecePart
    private lateinit var p2: PiecePart
    private lateinit var a: Assembly

    @BeforeEach
    fun setUp() {
        p1 = PiecePart("997624", "MyPart", 3.20)
        p2 = PiecePart("7734", "Hell", 666.0) // Kotlin은 Double 명시
        a = Assembly("5879", "MyAssembly")
    }

    @Test
    fun createPart() {
        assertEquals("997624", p1.partNumber)
        assertEquals("MyPart", p1.description)
        assertEquals(3.20, p1.cost, 0.01) // delta for double comparison
    }

    @Test
    fun createAssembly() {
        assertEquals("5879", a.partNumber)
        assertEquals("MyAssembly", a.description)
    }

    @Test
    fun assemblyContents() { // Renamed from Assembly to avoid conflict
        a.add(p1)
        a.add(p2)
        assertEquals(2, a.parts.size)

        val part0 = a.parts[0] as? PiecePart // Safe cast
        assertEquals(p1, part0)

        val part1 = a.parts[1] as? PiecePart
        assertEquals(p2, part1)
    }

    @Test
    fun assemblyOfAssemblies() {
        val subAssembly = Assembly("1324", "SubAssembly")
        subAssembly.add(p1)
        a.add(subAssembly)

        assertEquals(subAssembly, a.parts[0])
    }

    private class TestingVisitor : PartVisitor {
        val visitedParts = mutableListOf<Part>()

        override fun visit(p: PiecePart) {
            visitedParts.add(p)
        }

        override fun visit(assy: Assembly) {
            visitedParts.add(assy)
        }
    }

    @Test
    fun visitorCoverage() {
        a.add(p1)
        a.add(p2)

        val visitor = TestingVisitor()
        a.accept(visitor)

        assertTrue(visitor.visitedParts.contains(p1))
        assertTrue(visitor.visitedParts.contains(p2))
        assertTrue(visitor.visitedParts.contains(a))
    }

    private lateinit var cellphone: Assembly

    private fun setUpReportDatabase() {
        cellphone = Assembly("CP-7734", "Cell Phone")
        val display = PiecePart("DS-1428", "LCD Display", 14.37)
        val speaker = PiecePart("SP-92", "Speaker", 3.50)
        val microphone = PiecePart("MC-28", "Microphone", 5.30)
        val cellRadio = PiecePart("CR-56", "Cell Radio", 30.0)
        val frontCover = PiecePart("FC-77", "Front Cover", 1.4)
        val backCover = PiecePart("RC-77", "RearCover", 1.2)
        val keypad = Assembly("KP-62", "Keypad")
        val button = Assembly("B52", "Button") // This is an Assembly in C#
        val buttonCover = PiecePart("CV-15", "Cover", 0.5)
        val buttonContact = PiecePart("CN-2", "Contact", 1.2)

        button.add(buttonCover)
        button.add(buttonContact)
        for (i in 0 until 15) { // Kotlin's range
            keypad.add(button) // Adding the same button assembly 15 times
        }
        cellphone.add(display)
        cellphone.add(speaker)
        cellphone.add(microphone)
        cellphone.add(cellRadio)
        cellphone.add(frontCover)
        cellphone.add(backCover)
        cellphone.add(keypad)
    }

    @Test
    fun explodedCost() {
        setUpReportDatabase()
        val v = ExplodedCostVisitor()
        cellphone.accept(v)
        assertEquals(81.27, v.cost, 0.001) // Adjusted delta for precision
    }

    @Test
    fun partCount() {
        setUpReportDatabase()
        val v = PartCountVisitor()
        cellphone.accept(v)

        // Cost of button: 0.5 (cover) + 1.2 (contact) = 1.7
        // Cost of keypad: 15 * 1.7 = 25.5
        // Total PieceParts: display(1) + speaker(1) + mic(1) + radio(1) + front(1) + back(1) + (buttonCover(1) + buttonContact(1)) * 15 = 6 + 2*15 = 36
        assertEquals(36, v.pieceCount)

        // Unique PiecePart numbers: DS-1428, SP-92, MC-28, CR-56, FC-77, RC-77, CV-15, CN-2
        assertEquals(8, v.partNumberCount)
        assertEquals(1, v.getCountForPart("DS-1428"), "DS-1428 count")
        assertEquals(1, v.getCountForPart("SP-92"), "SP-92 count")
        assertEquals(1, v.getCountForPart("MC-28"), "MC-28 count")
        assertEquals(1, v.getCountForPart("CR-56"), "CR-56 count")
        // The C# test had "FC-77" missing but "RC-77" present. Assuming FC-77 should also be 1.
        assertEquals(1, v.getCountForPart("FC-77"), "FC-77 count")
        assertEquals(1, v.getCountForPart("RC-77"), "RC-77 count")

        assertEquals(15, v.getCountForPart("CV-15"), "CV-15 count") // Button covers
        assertEquals(15, v.getCountForPart("CN-2"), "CN-2 count") // Button contacts
        assertEquals(0, v.getCountForPart("Bob"), "Bob count")
    }
}
