package proxy_pattern.table_gateway_pattern.infra.persistence

import proxy_pattern.table_gateway_pattern.domain.gateway.ProductGateway
import proxy_pattern.table_gateway_pattern.domain.model.Product
import java.sql.Connection
import java.sql.SQLException

class DbProductGateway(
    private val connection: Connection,
) : ProductGateway {

    override fun insert(product: Product) {
        val sql = "insert into Products (sku, name, price) values (?, ?, ?)"
        try {
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, product.sku)
                stmt.setString(2, product.name)
                stmt.setInt(3, product.price)
                stmt.executeUpdate()
            }
        } catch (e: SQLException) {
            // Log error or rethrow as custom exception
            throw RuntimeException("Error inserting product: ${product.sku}", e)
        }
    }

    override fun find(sku: String): Product? {
        val sql = "select sku, name, price from Products where sku = ?"
        var product: Product? = null
        try {
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, sku)
                stmt.executeQuery().use { rs ->
                    if (rs.next()) {
                        val name = rs.getString("name")
                        val price = rs.getInt("price")
                        // sku is known from parameter
                        product = Product(name, sku, price)
                    }
                }
            }
        } catch (e: SQLException) {
            // Log error or rethrow
            throw RuntimeException("Error finding product with SKU: $sku", e)
        }
        return product
    }
}
