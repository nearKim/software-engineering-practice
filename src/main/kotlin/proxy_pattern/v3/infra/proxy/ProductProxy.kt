package proxy_pattern.v3.infra.proxy

import proxy_pattern.v3.domain.Product
import proxy_pattern.v3.infra.ProductData
import proxy_pattern.v3.infra.persistence.Db

/**
 * Proxy for Product, enabling lazy loading of product details from the database.
 */
class ProductProxy(
    override val sku: String,
) : Product {
    private var realProductData: ProductData? = null

    private fun getProductData(): ProductData {
        if (realProductData == null) {
            realProductData = Db.getProductBySku(sku)
                ?: throw IllegalStateException("Product with SKU $sku not found.")
        }
        return realProductData!!
    }

    override val name: String
        get() = getProductData().name

    override val price: Int
        get() = getProductData().price
}
