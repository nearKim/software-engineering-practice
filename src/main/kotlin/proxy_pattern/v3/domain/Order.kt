package proxy_pattern.v3.domain

interface Order {
    val customerId: String

    fun addItem(
        p: Product,
        quantity: Int,
    )

    val total: Int
}
