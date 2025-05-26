package proxy_pattern.table_gateway_pattern.test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import proxy_pattern.table_gateway_pattern.domain.model.Product
import proxy_pattern.table_gateway_pattern.infra.persistence.DbProductGateway

class DbProductGatewayTest : AbstractDbGatewayTest() {
    private lateinit var gateway: DbProductGateway

    @BeforeEach
    fun setUp() { // Renamed from openConnection to avoid override confusion if not intended
        super.setUpDatabaseConnection() // Call base class setup
        gateway = DbProductGateway(super.connection!!) // Use the connection from base class
        try {
            executeSql("DELETE FROM Products")
        } catch (e: Exception) {
            // If tables don't exist, this might fail. For robust testing, schema creation might be needed.
            System.err.println("Warning: Could not clear Products table. It might not exist or DB issue: ${e.message}")
        }
    }

    @Test
    fun insert() {
        val product = Product("Peanut Butter", "pb", 3)
        gateway.insert(product)

        // Verify by querying the database directly
        var found = false
        connection!!.prepareStatement("SELECT sku, name, price FROM Products WHERE sku = ?").use { stmt ->
            stmt.setString(1, "pb")
            this.resultSet = stmt.executeQuery() // Assign to class field to mimic C# test style

            if (this.resultSet!!.next()) {
                found = true
                assertEquals("pb", this.resultSet!!.getString("sku"))
                assertEquals("Peanut Butter", this.resultSet!!.getString("name"))
                assertEquals(3, this.resultSet!!.getInt("price"))
            }
        }
        assertTrue(found, "Product was not inserted or found.")
    }

    @Test
    fun find() {
        val pb = Product("Peanut Butter", "pb", 3)
        val jam = Product("Strawberry Jam", "jam", 2)

        gateway.insert(pb)
        gateway.insert(jam)

        assertNull(gateway.find("bad sku"), "Finding non-existent SKU should return null.")

        val foundPb = gateway.find(pb.sku)
        assertNotNull(foundPb, "Failed to find existing product 'pb'.")
        checkThatProductsMatch(pb, foundPb!!)

        val foundJam = gateway.find(jam.sku)
        assertNotNull(foundJam, "Failed to find existing product 'jam'.")
        checkThatProductsMatch(jam, foundJam!!)
    }

    private fun checkThatProductsMatch(
        expected: Product,
        actual: Product,
    ) {
        assertEquals(expected.name, actual.name, "Product names do not match.")
        assertEquals(expected.sku, actual.sku, "Product SKUs do not match.")
        assertEquals(expected.price, actual.price, "Product prices do not match.")
    }
}
