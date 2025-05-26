package proxy_pattern.table_gateway_pattern.domain.gateway

import proxy_pattern.table_gateway_pattern.domain.model.Order

interface OrderGateway {
    fun insert(order: Order)

    fun find(id: Int): Order?
}
