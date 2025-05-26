package proxy_pattern.table_gateway_pattern.infra.persistence

import proxy_pattern.table_gateway_pattern.domain.gateway.OrderGateway
import proxy_pattern.table_gateway_pattern.domain.gateway.ProductGateway
import proxy_pattern.table_gateway_pattern.domain.model.Order
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class DbOrderGateway(
    private val connection: Connection,
    private val productGateway: ProductGateway, // Used to find products for items
) : OrderGateway {
    override fun insert(order: Order) {
        val sqlOrder = "insert into Orders (cusId) values (?)"
        try {
            // Insert Order and get generated ID
            connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS).use { stmt ->
                stmt.setString(1, order.customerId)
                stmt.executeUpdate()
                stmt.generatedKeys.use { generatedKeys ->
                    if (generatedKeys.next()) {
                        order.id = generatedKeys.getInt(1)
                    } else {
                        throw SQLException("Creating order failed, no ID obtained.")
                    }
                }
            }
            // Insert Items for the Order
            insertItems(order)
        } catch (e: SQLException) {
            // Consider transaction rollback if connection is autoCommit=false
            throw RuntimeException("Error inserting order for customer: ${order.customerId}", e)
        }
    }

    override fun find(id: Int): Order? {
        val sqlOrder = "select orderId, cusId from Orders where orderId = ?"
        var order: Order? = null
        try {
            connection.prepareStatement(sqlOrder).use { stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use { rs ->
                    if (rs.next()) {
                        val customerId = rs.getString("cusId")
                        // val orderIdFromDb = rs.getInt("orderId") // id is already known parameter
                        order =
                            Order(customerId).apply {
                                this.id = id // Set the known ID
                            }
                    }
                }
            }

            order?.let {
                loadItems(it) // Load associated items if order was found
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error finding order with ID: $id", e)
        }
        return order
    }

    private fun loadItems(order: Order) {
        val sqlItems = "select sku, quantity from Items where orderId = ?"
        try {
            connection.prepareStatement(sqlItems).use { stmt ->
                stmt.setInt(1, order.id)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val sku = rs.getString("sku")
                        val quantity = rs.getInt("quantity")

                        if (sku != null) {
                            val product = productGateway.find(sku) // Use ProductGateway to find product
                            if (product != null) {
                                order.addItem(product, quantity)
                            } else {
                                // Log or handle missing product for an item, crucial for data integrity
                                System.err.println("Warning: Product with SKU '$sku' for order '${order.id}' not found during loadItems.")
                            }
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error loading items for order ID: ${order.id}", e)
        }
    }

    private fun insertItems(order: Order) {
        val sqlItem = "insert into Items (orderId, quantity, sku) values (?, ?, ?)"
        try {
            // Batch insert could be more efficient for many items
            connection.prepareStatement(sqlItem).use { stmt ->
                order.items.forEach { item ->
                    stmt.setInt(1, order.id)
                    stmt.setInt(2, item.quantity)
                    stmt.setString(3, item.product.sku)
                    stmt.addBatch() // Add to batch
                }
                stmt.executeBatch() // Execute all batched inserts
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error inserting items for order ID: ${order.id}", e)
        }
    }
}
