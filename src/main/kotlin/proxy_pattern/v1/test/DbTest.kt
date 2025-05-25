package proxy_pattern.v1.test

import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import proxy_pattern.v1.Db
import proxy_pattern.v1.models.ProductData

class DBTest {
    @BeforeEach
    fun setUp() {
        Db.init()
    }

    @AfterEach
    fun tearDown() {
        Db.close()
    }

    @Test
    fun storeProduct() {
        val storedProduct =
            ProductData("MyProduct", 1234, "999")
        Db.store(storedProduct)
        val retrievedProduct = Db.getProductData("999")
        Db.deleteProductData("999")
        Assertions.assertEquals(storedProduct, retrievedProduct)
    }
}
