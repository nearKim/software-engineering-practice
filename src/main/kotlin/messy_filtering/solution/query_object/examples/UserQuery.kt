package messy_filtering.solution.query_object.examples

import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root

data class UserQuery(
    val isActive: Boolean? = null,
    val minAge: Int? = null,
    val ldap: String? = null,
    val sortBy: String? = null,
    val sortDirection: String = "ASC",
    val page: Int = 0,
    val size: Int = 10,
) {
    fun toCriteriaQuery(em: EntityManager): CriteriaQuery<User> {
        val cb: CriteriaBuilder = em.criteriaBuilder
        val query: CriteriaQuery<User> = cb.createQuery(User::class.java)
        val root: Root<User> = query.from(User::class.java)

        // Build predicates for filters
        val predicates = mutableListOf<Predicate>()
        isActive?.let { predicates.add(cb.equal(root.get<Boolean>("isActive"), it)) }
        minAge?.let { predicates.add(cb.greaterThan(root.get<Int>("age"), it)) }
        ldap?.let { predicates.add(cb.equal(root.get<String>("ldap"), it)) }

        // Apply filters if any
        if (predicates.isNotEmpty()) {
            query.where(*predicates.toTypedArray())
        }

        // Apply sorting if specified
        sortBy?.let {
            val order =
                if (sortDirection.uppercase() == "ASC") {
                    cb.asc(root.get<Any>(it))
                } else {
                    cb.desc(root.get<Any>(it))
                }
            query.orderBy(order)
        }

        return query
    }
}
