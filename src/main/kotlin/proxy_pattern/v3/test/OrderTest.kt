package proxy_pattern.v3.test

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import proxy_pattern.v3.infra.*
import proxy_pattern.v3.infra.persistence.Db
import proxy_pattern.v3.infra.proxy.*

class OrderTests {
    @BeforeEach
    fun setUp() {
        Db.clear() // Clear database before each test
    }

    @AfterEach
    fun tearDown() {
        Db.clear() // Clear database after each test
    }

    @Test
    fun orderProxyTotal() {
        Db.store(ProductData("Wheaties", 349, "wheaties"))
        Db.store(ProductData("Crest", 258, "crest"))

        val wheaties = ProductProxy("wheaties")
        val crest = ProductProxy("crest")

        val od = Db.newOrder("testOrderProxy")
        val order = OrderProxy(od.orderId)

        order.addItem(crest, 1) // 258 * 1 = 258
        order.addItem(wheaties, 2) // 349 * 2 = 698
        // Total = 258 + 698 = 956
        assertEquals(956, order.total)
    }

    @Test
    fun orderKeyGeneration() {
        val o1 = Db.newOrder("Bob")
        val o2 = Db.newOrder("Bill")
        val firstOrderId = o1.orderId
        val secondOrderId = o2.orderId
        assertEquals(firstOrderId + 1, secondOrderId)
    }

    @Test
    fun storeItem() {
        // Need an order to associate the item with
        val orderData = Db.newOrder("testCustomer")
        val storedItem = ItemData(orderData.orderId, 3, "sku123")
        Db.store(ProductData("Test Product", 100, "sku123")) // Ensure product exists for proxy
        Db.store(storedItem)

        val retrievedItems = Db.getItemsForOrder(orderData.orderId)
        assertEquals(1, retrievedItems.size)
        // ItemData is a data class, so assertEquals works by comparing property values
        assertEquals(storedItem, retrievedItems[0])
    }

    @Test
    fun noItems() {
        val items = Db.getItemsForOrder(42) // Assuming orderId 42 does not exist or has no items
        assertEquals(0, items.size)
    }
}
