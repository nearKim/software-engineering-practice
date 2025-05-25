package proxy_pattern.v2.test

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import proxy_pattern.v2.Db
import proxy_pattern.v2.ProductProxy
import proxy_pattern.v2.models.ProductData

class ProxyTest {
    @BeforeEach
    fun setUp() {
        Db.init()
        val pd =
            ProductData(
                sku = "ProxyTest1",
                name = "ProxyTestName1",
                price = 456,
            )
        Db.store(pd)
    }

    @AfterEach
    fun tearDown() {
        Db.deleteProductData("ProxyTest1")
        Db.close()
    }

    @Test
    fun productProxy() {
        val p = ProductProxy("ProxyTest1")
        Assertions.assertEquals(456, p.price)
        Assertions.assertEquals("ProxyTestName1", p.name)
        Assertions.assertEquals("ProxyTest1", p.sku)
    }
}
