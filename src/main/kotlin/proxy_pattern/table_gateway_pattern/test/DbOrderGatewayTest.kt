package proxy_pattern.table_gateway_pattern.test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import proxy_pattern.table_gateway_pattern.domain.gateway.ProductGateway
import proxy_pattern.table_gateway_pattern.domain.model.Order
import proxy_pattern.table_gateway_pattern.domain.model.Product
import proxy_pattern.table_gateway_pattern.infra.persistence.DbOrderGateway
import proxy_pattern.table_gateway_pattern.infra.persistence.InMemoryProductGateway
import java.sql.Statement

class DbOrderGatewayTest : AbstractDbGatewayTest() {
    private lateinit var gateway: DbOrderGateway
    private lateinit var pizza: Product
    private lateinit var beer: Product

    @BeforeEach
    fun setUp() {
        super.setUpDatabaseConnection()

        pizza = Product("Pizza", "pizza", 15)
        beer = Product("Beer", "beer", 2)

        val productGateway: ProductGateway = InMemoryProductGateway() // Products for order items are from memory
        productGateway.insert(pizza)
        productGateway.insert(beer)

        gateway = DbOrderGateway(super.connection!!, productGateway)
        try {
            executeSql("DELETE FROM Items")
            executeSql("DELETE FROM Orders")
        } catch (e: Exception) {
            System.err.println("Warning: Could not clear Orders/Items tables: ${e.message}")
        }
    }

    @Test
    fun find() {
        var orderId = -1
        // Manually insert order and items for find test
        val orderInsertSql = "INSERT INTO Orders (cusId) VALUES (?)"
        connection!!.prepareStatement(orderInsertSql, Statement.RETURN_GENERATED_KEYS).use { stmt ->
            stmt.setString(1, "Snoopy")
            stmt.executeUpdate()
            stmt.generatedKeys.use { gk ->
                if (gk.next()) {
                    orderId = gk.getInt(1)
                } else {
                    fail("Failed to insert test order and retrieve ID.")
                }
            }
        }

        executeSql("INSERT INTO Items (orderId, quantity, sku) VALUES ($orderId, 1, 'pizza')")
        executeSql("INSERT INTO Items (orderId, quantity, sku) VALUES ($orderId, 6, 'beer')")

        val order = gateway.find(orderId)

        assertNotNull(order, "Order should be found.")
        order?.let {
            assertEquals("Snoopy", it.customerId, "Customer ID mismatch.")
            assertEquals(2, it.itemCount, "Item count mismatch.")
            assertEquals(1, it.quantityOf(pizza), "Pizza quantity mismatch.")
            assertEquals(6, it.quantityOf(beer), "Beer quantity mismatch.")
        }
    }

    @Test
    fun insert() {
        val order = Order("Snoopy")
        order.addItem(pizza, 1)
        order.addItem(beer, 6)

        gateway.insert(order)

        assertTrue(order.id > 0, "Order ID should be assigned by the gateway after insert.")

        val foundOrder = gateway.find(order.id)
        assertNotNull(foundOrder, "Inserted order could not be found.")
        foundOrder?.let {
            assertEquals("Snoopy", it.customerId)
            assertEquals(2, it.itemCount)
            assertEquals(1, it.quantityOf(pizza))
            assertEquals(6, it.quantityOf(beer))
        }
    }
}
