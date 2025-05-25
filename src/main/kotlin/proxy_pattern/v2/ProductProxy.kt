package proxy_pattern.v2

import proxy_pattern.v2.models.Product

// Infrastructure Layer
class ProductProxy(
    override val sku: String,
) : Product {
    private val productData by lazy { Db.getProductData(sku) }

    override val price: Int by lazy {
        productData.price
    }

    override val name: String by lazy {
        productData.name
    }
}