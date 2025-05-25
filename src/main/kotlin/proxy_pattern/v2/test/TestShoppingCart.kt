package proxy_pattern.v2.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import proxy_pattern.v2.models.Product
import proxy_pattern.v2.models.ProductImpl

class TestShoppingCart {
    @Test
    fun addProductToCart() {
        val cart = ShoppingCart() // FIXME: not implemented
        val p: Product = ProductImpl("TestSku", "TestName", 100)
        cart.addProduct(p)
        Assertions.assertEquals(1, cart.productCount)
        Assertions.assertEquals(100, cart.totalPrice)
    }
}