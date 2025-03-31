package messy_filtering.solution.kotlin_dsl

import jakarta.persistence.EntityManager
import messy_filtering.solution.kotlin_dsl.domain.User

interface UserRepository {
    fun findByQuery(
        block: QueryBuilder<User>.() -> Unit,
        page: Int = 0,
        size: Int = 10,
    ): List<User>
}

class UserRepositoryImpl(
    private val em: EntityManager,
) : UserRepository {
    override fun findByQuery(
        block: QueryBuilder<User>.() -> Unit,
        page: Int,
        size: Int,
    ): List<User> {
        val query = QueryBuilder(em, User::class).apply(block).build()
        val typedQuery = em.createQuery(query)
        typedQuery.firstResult = page * size
        typedQuery.maxResults = size
        return typedQuery.resultList
    }
}
