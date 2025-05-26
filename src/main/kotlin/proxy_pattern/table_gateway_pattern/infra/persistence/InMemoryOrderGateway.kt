package proxy_pattern.table_gateway_pattern.infra.persistence

import proxy_pattern.table_gateway_pattern.domain.gateway.OrderGateway
import proxy_pattern.table_gateway_pattern.domain.model.Order

class InMemoryOrderGateway : OrderGateway {
    private val orders = mutableMapOf<Int, Order>()
    private var nextId = 1

    override fun insert(order: Order) {
        order.id = nextId // Assign ID
        orders[nextId++] = order
    }

    override fun find(id: Int): Order? = orders[id]
}
