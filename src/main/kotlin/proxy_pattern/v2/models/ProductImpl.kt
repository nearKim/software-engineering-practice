package proxy_pattern.v2.models

class ProductImpl(
    override val sku: String,
    override val name: String,
    override val price: Int,
) : Product
