package proxy_pattern.v3.infra

data class ItemData(
    val orderId: Int = 0,
    val qty: Int = 0,
    val sku: String = "junk",
) {
    override fun equals(other: Any?): Boolean {
        if (other is ItemData) {
            return orderId == other.orderId &&
                qty == other.qty &&
                sku == other.sku
        }
        return false
    }

    override fun hashCode(): Int {
        var result = orderId
        result = 31 * result + qty
        result = 31 * result + sku.hashCode()
        return result
    }
}
