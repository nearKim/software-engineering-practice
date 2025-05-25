package proxy_pattern.v3.infra.proxy

import proxy_pattern.v3.domain.Order
import proxy_pattern.v3.domain.OrderImpl
import proxy_pattern.v3.domain.Product
import proxy_pattern.v3.infra.ItemData
import proxy_pattern.v3.infra.persistence.Db

/**
 * Proxy for Order, handling data fetching and delegation to OrderImpl.
 */
class OrderProxy(
    private val orderId: Int,
) : Order {
    override val total: Int
        get() {
            // Create a temporary OrderImpl instance to calculate the total based on fetched items
            val imp = OrderImpl(customerId) // CustomerId will be fetched
            val itemDataArray = Db.getItemsForOrder(orderId)
            for (itemData in itemDataArray) {
                // For each item data, create a ProductProxy to represent the product
                // This assumes ProductProxy can fetch product details based on SKU
                imp.addItem(ProductProxy(itemData.sku), itemData.qty)
            }
            return imp.total
        }

    override val customerId: String
        get() {
            val od = Db.getOrderData(orderId)
            return od?.customerId
                ?: throw IllegalStateException("Order with ID $orderId not found or has no customer ID.")
        }

    override fun addItem(
        product: Product,
        quantity: Int,
    ) {
        // When adding an item through the proxy, it directly stores item data
        val id = ItemData(orderId, quantity, product.sku)
        Db.store(id)
    }
}
