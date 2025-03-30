package messy_filtering.solution.query_object.jpa

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.*

/**
 * QueryObject with Criteria API Replaces Criteria and QueryObject
 * Your original QueryObject aggregates Criteria to build SQL WHERE clauses.
 * With JPA’s Criteria API, you can create a new QueryObject that encapsulates all query logic—filters, sorting, and pagination—in a type-safe, framework-supported way.
 */

data class PersonQuery(
    val minDependents: Int? = null,
    val lastNamePattern: String? = null,
    val sortBy: String? = null,
    val sortDirection: String = "ASC",
    val page: Int = 0,
    val size: Int = 10,
) {
    fun toCriteriaQuery(em: EntityManager): CriteriaQuery<Person> {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<Person> = cb.createQuery(Person::class.java)
        val root: Root<Person> = query.from(Person::class.java)

        // Apply filters
        val predicates = mutableListOf<Predicate>()
        minDependents?.let { predicates.add(cb.greaterThan(root.get("numberOfDependents"), it)) }
        lastNamePattern?.let { predicates.add(cb.like(cb.upper(root.get("lastName")), "%${it.uppercase()}%")) }
        if (predicates.isNotEmpty()) {
            query.where(*predicates.toTypedArray())
        }

        // Apply sorting
        sortBy?.let {
            val order =
                if (sortDirection.uppercase() == "ASC") cb.asc(root.get<Any>(it)) else cb.desc(root.get<Any>(it))
            query.orderBy(order)
        }

        return query
    }
}
