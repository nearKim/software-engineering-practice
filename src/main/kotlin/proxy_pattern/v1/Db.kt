package proxy_pattern.v1

import proxy_pattern.v1.models.ProductData
import java.sql.Connection
import java.sql.DriverManager

object Db {
    private var connection: Connection? = null

    fun init() {
        val connectionString = "jdbc:sqlserver://marvin;databaseName=QuickyMart;user=sa;password=abc;"
        connection = DriverManager.getConnection(connectionString)
    }

    fun store(pd: ProductData) {
        val sql = "INSERT INTO Products VALUES (?, ?, ?)"
        val command = connection?.prepareStatement(sql)
        command?.setString(1, pd.sku)
        command?.setString(2, pd.name)
        command?.setInt(3, pd.price)
        command?.executeUpdate()
    }

    fun getProductData(sku: String): ProductData {
        val sql = "SELECT * FROM Products WHERE sku = ?"
        val command = connection?.prepareStatement(sql)
        command?.setString(1, sku)
        val resultSet = command?.executeQuery()
        resultSet?.next()
        val sku = resultSet?.getString("sku") ?: ""
        val name = resultSet?.getString("name") ?: ""
        val price = resultSet?.getInt("price") ?: 0
        resultSet?.close()
        return ProductData(name, price, sku)
    }

    fun deleteProductData(sku: String) {
        val sql = "DELETE FROM Products WHERE sku = ?"
        val command = connection?.prepareStatement(sql)
        command?.setString(1, sku)
        command?.executeUpdate()
    }

    fun close() {
        connection?.close()
    }
}