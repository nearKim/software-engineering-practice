package proxy_pattern.v3.domain

class OrderImpl(
    override val customerId: String,
) : Order {
    private val items = mutableListOf<Item>()

    override fun addItem(
        product: Product,
        quantity: Int,
    ) {
        val item = Item(product, quantity)
        items.add(item)
    }

    override val total: Int
        get() {
            var currentTotal = 0
            for (item in items) {
                currentTotal += item.product.price * item.quantity
            }
            return currentTotal
        }
}
