package proxy_pattern.v1.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import proxy_pattern.v1.models.ProductData

class TestShoppingCart {
    @Test
    fun addProductToCart() {
        val cart = ShoppingCart() // FIXME: Not implemented
        val p = ProductData("TestSku", 100, "999")
        cart.addProduct(p)
        Assertions.assertEquals(1, cart.productCount)
        Assertions.assertEquals(100, cart.totalPrice)
    }
}
