package proxy_pattern.v3.infra.persistence

import proxy_pattern.v3.infra.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * Simulates a database using in-memory collections.
 * In a real application, this would interact with an actual database.
 */
object Db {
    private val productTable = mutableMapOf<String, ProductData>()
    private val orderTable = mutableMapOf<Int, OrderData>()
    private val itemTable = mutableListOf<ItemData>()
    private val orderIdCounter = AtomicInteger(0)

    // Simulate SQL Command and Connection conceptually
    private class SqlCommand(
        val sql: String,
        val parameters: Map<String, Any> = emptyMap(),
    ) {
        fun executeScalar(): Any? {
            // Simplified simulation for NewOrder
            if (sql.startsWith("INSERT INTO Orders") && sql.contains("SELECT scope_identity()")) {
                val customerId = parameters["@cusId"] as String
                val newOrderId = orderIdCounter.incrementAndGet()
                orderTable[newOrderId] = OrderData(newOrderId, customerId)
                return newOrderId
            }
            return null
        }

        fun executeNonQuery() {
            // Simplified simulation for Store ItemData and Clear
            if (sql.startsWith("INSERT INTO Items")) {
                val orderId = parameters["@orderId"] as Int
                val quantity = parameters["@quantity"] as Int
                val sku = parameters["@sku"] as String
                itemTable.add(ItemData(orderId, quantity, sku))
            } else if (sql == "DELETE FROM Items") {
                itemTable.clear()
            } else if (sql == "DELETE FROM Orders") {
                orderTable.clear()
                orderIdCounter.set(0) // Reset counter for orders as well
            } else if (sql == "DELETE FROM Products") {
                productTable.clear()
            }
        }

        fun executeReader(): IDataReader {
            // Simplified simulation for GetItemsForOrder and GetOrderData
            val results = mutableListOf<Map<String, Any>>()
            if (sql.startsWith("SELECT * FROM Items WHERE orderid = @orderId")) {
                val orderIdParam = parameters["@orderId"] as Int
                itemTable.filter { it.orderId == orderIdParam }.forEach {
                    results.add(mapOf("orderId" to it.orderId, "quantity" to it.qty, "sku" to it.sku))
                }
            } else if (sql.startsWith("SELECT cusid FROM orders WHERE orderid = @orderId")) {
                val orderIdParam = parameters["@orderId"] as Int
                orderTable[orderIdParam]?.let {
                    results.add(mapOf("cusid" to it.customerId))
                }
            }
            return SimulatedDataReader(results)
        }
    }

    // Simulate IDataReader conceptually
    private interface IDataReader {
        fun read(): Boolean

        fun getString(columnName: String): String

        fun getInt(columnName: String): Int

        fun close()
    }

    private class SimulatedDataReader(
        private val results: List<Map<String, Any>>,
    ) : IDataReader {
        private var currentIndex = -1
        private var currentRow: Map<String, Any>? = null

        override fun read(): Boolean {
            currentIndex++
            return if (currentIndex < results.size) {
                currentRow = results[currentIndex]
                true
            } else {
                currentRow = null
                false
            }
        }

        override fun getString(columnName: String): String = currentRow?.get(columnName).toString()

        override fun getInt(columnName: String): Int = (currentRow?.get(columnName) as Number).toInt()

        override fun close() { // No-op for simulation
        }
    }

    fun store(productData: ProductData) {
        productTable[productData.sku] = productData
        // In a real DB, this would be an INSERT or UPDATE SQL command
        println("Stored Product: ${productData.sku}")
    }

    fun getProductBySku(sku: String): ProductData? {
        return productTable[sku]
        // In a real DB, this would be a SELECT SQL command
    }

    fun newOrder(customerId: String): OrderData {
        val sql = "INSERT INTO Orders(cusId) VALUES(@cusId); SELECT scope_identity()"
        val command = SqlCommand(sql, mapOf("@cusId" to customerId))
        val newOrderId = command.executeScalar() as Int
        return OrderData(newOrderId, customerId) // The OrderData is already stored by executeScalar simulation
    }

    fun store(id: ItemData) {
        val command = buildItemInsertionStatement(id)
        command.executeNonQuery()
    }

    private fun buildItemInsertionStatement(id: ItemData): SqlCommand {
        val sql = "INSERT INTO Items(orderId,quantity,sku) VALUES (@orderID, @quantity, @sku)"
        return SqlCommand(
            sql,
            mapOf(
                "@orderId" to id.orderId,
                "@quantity" to id.qty,
                "@sku" to id.sku,
            ),
        )
    }

    fun getItemsForOrder(orderId: Int): Array<ItemData> {
        val command = buildItemsForOrderQueryStatement(orderId)
        val reader = command.executeReader()
        val items = extractItemDataFromResultSet(reader)
        reader.close()
        return items.toTypedArray()
    }

    private fun buildItemsForOrderQueryStatement(orderId: Int): SqlCommand {
        val sql = "SELECT * FROM Items WHERE orderid = @orderId"
        return SqlCommand(sql, mapOf("@orderId" to orderId))
    }

    private fun extractItemDataFromResultSet(reader: IDataReader): List<ItemData> {
        val items = mutableListOf<ItemData>()
        while (reader.read()) {
            val orderId = reader.getInt("orderId")
            val quantity = reader.getInt("quantity")
            val sku = reader.getString("sku")
            items.add(ItemData(orderId, quantity, sku))
        }
        return items
    }

    fun getOrderData(orderId: Int): OrderData? {
        val sql = "SELECT cusid FROM orders WHERE orderid = @orderId"
        val command = SqlCommand(sql, mapOf("@orderId" to orderId))
        val reader = command.executeReader()

        var od: OrderData? = null
        if (reader.read()) {
            od = OrderData(orderId, reader.getString("cusid"))
        }
        reader.close()
        return od
    }

    fun clear() {
        executeSql("DELETE FROM Items")
        executeSql("DELETE FROM Orders")
        executeSql("DELETE FROM Products")
    }

    private fun executeSql(sql: String) {
        val command = SqlCommand(sql)
        command.executeNonQuery()
    }
}
