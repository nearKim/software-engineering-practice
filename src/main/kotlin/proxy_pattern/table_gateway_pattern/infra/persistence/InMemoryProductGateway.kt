package proxy_pattern.table_gateway_pattern.infra.persistence

import proxy_pattern.table_gateway_pattern.domain.gateway.ProductGateway
import proxy_pattern.table_gateway_pattern.domain.model.Product

class InMemoryProductGateway : ProductGateway {
    private val products = mutableMapOf<String, Product>()

    override fun insert(product: Product) {
        products[product.sku] = product
    }

    override fun find(sku: String): Product? = products[sku]
}
