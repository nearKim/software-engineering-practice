package messy_filtering.solution.kotlin_dsl

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.*
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

class QueryBuilder<T : Any>(
    private val em: EntityManager,
    private val entityClass: KClass<T>,
) {
    private val joinSpecs = mutableListOf<Pair<KProperty1<T, *>, JoinType>>()
    private val conditions = mutableListOf<(CriteriaBuilder, Root<T>, Map<String, Join<*, *>>) -> Predicate>()
    private val orders = mutableListOf<(CriteriaBuilder, Root<T>) -> Order>()

    fun where(condition: (CriteriaBuilder, Root<T>, Map<String, Join<*, *>>) -> Predicate) {
        conditions += condition
    }

    fun orderBy(order: (CriteriaBuilder, Root<T>) -> Order) {
        orders += order
    }

    fun <R> join(
        property: KProperty1<T, R>,
        joinType: JoinType = JoinType.INNER,
    ) {
        joinSpecs.add(Pair(property, joinType))
    }

    fun build(): CriteriaQuery<T> {
        val cb = em.criteriaBuilder
        val query = cb.createQuery(entityClass.java)
        val root = query.from(entityClass.java)
        val joins = mutableMapOf<String, Join<*, *>>()
        for ((property, joinType) in joinSpecs) {
            val join = root.join<Any, Any>(property.name, joinType)
            joins[property.name] = join
        }

        val predicates = conditions.map { it(cb, root, joins) }
        if (predicates.isNotEmpty()) {
            query.where(*predicates.toTypedArray())
        }
        val orderList = orders.map { it(cb, root) }
        if (orderList.isNotEmpty()) {
            query.orderBy(*orderList.toTypedArray())
        }
        return query
    }
}
