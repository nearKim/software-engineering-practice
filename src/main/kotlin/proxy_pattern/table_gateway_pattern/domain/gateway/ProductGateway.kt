package proxy_pattern.table_gateway_pattern.domain.gateway

import proxy_pattern.table_gateway_pattern.domain.model.Product

interface ProductGateway {
    fun insert(product: Product)

    fun find(sku: String): Product?
}
