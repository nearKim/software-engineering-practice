package proxy_pattern.v1.models

data class ProductData(
    val name: String = "",
    val price: Int = 0,
    val sku: String = "",
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProductData) return false
        return name == other.name && sku == other.sku && price == other.price
    }

    override fun hashCode(): Int = name.hashCode() xor sku.hashCode() xor price.hashCode()
}
