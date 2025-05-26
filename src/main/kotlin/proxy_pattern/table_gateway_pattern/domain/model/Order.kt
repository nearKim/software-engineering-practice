package proxy_pattern.table_gateway_pattern.domain.model

class Order(
    val customerId: String,
) {
    var id: Int = 0 // Default ID, typically set after insertion by gateway
    private val _items = mutableListOf<Item>()

    val items: List<Item>
        get() = _items.toList()

    val itemCount: Int
        get() = _items.size

    fun addItem(
        product: Product,
        quantity: Int,
    ) {
        // Consider if item for the same product should be merged or replaced
        _items.add(Item(product, quantity))
    }

    fun quantityOf(product: Product): Int = _items.find { it.product.sku == product.sku }?.quantity ?: 0

    val total: Int
        get() = _items.sumOf { it.product.price * it.quantity }
}
