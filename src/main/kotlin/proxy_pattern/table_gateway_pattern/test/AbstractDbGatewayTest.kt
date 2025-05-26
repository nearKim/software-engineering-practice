package proxy_pattern.table_gateway_pattern.test

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

abstract class AbstractDbGatewayTest {
    protected var connection: Connection? = null // Nullable, as it's initialized in setup
    protected var resultSet: ResultSet? = null // To mirror C# field, use with caution

    // NOTE: For these tests to run, a SQL Server (or other configured DB) must be running
    // and accessible with the specified connection string.
    // The database "QuickyMart" and tables "Products", "Orders", "Items" must exist.
    // The SQL Server JDBC driver must be in the classpath.
    private val dbUrl =
        "jdbc:sqlserver://marvin:1433;databaseName=QuickyMart;user=sa;password=abc;encrypt=true;trustServerCertificate=true;"
    // If using a local H2 for easier testing:
    // private val dbUrl = "jdbc:h2:mem:quickymart;DB_CLOSE_DELAY=-1"
    // private val dbUser = "sa"
    // private val dbPassword = ""

    @BeforeEach
    open fun setUpDatabaseConnection() {
        try {
            // For SQL Server, ensure the driver is loaded (often automatic with JDBC 4.0+)
            // Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
            connection = DriverManager.getConnection(dbUrl)
            // For H2 example:
            // connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)
            // If using H2, you might want to run schema creation scripts here.
        } catch (e: Exception) {
            // Fail test or log if connection cannot be established
            throw RuntimeException("Failed to connect to database for testing: ${e.message}", e)
        }
    }

    @AfterEach
    open fun closeDatabaseResources() {
        try {
            resultSet?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            resultSet = null
        }
        try {
            connection?.close()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            connection = null
        }
    }

    protected fun executeSql(sql: String) {
        // For simple DML/DDL without parameters
        // Be cautious with this in tests if it modifies state expected by other parts of the test
        // or if it should be part of a transaction.
        requireNotNull(connection) { "Database connection is not initialized." }
        try {
            connection!!.createStatement().use { stmt ->
                stmt.executeUpdate(sql)
            }
        } catch (e: SQLException) {
            throw RuntimeException("Error executing SQL: $sql", e)
        }
    }
}
