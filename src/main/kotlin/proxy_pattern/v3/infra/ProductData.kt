package proxy_pattern.v3.infra

import proxy_pattern.v3.domain.Product

data class ProductData(
    override val name: String,
    override val price: Int,
    override val sku: String,
) : Product
