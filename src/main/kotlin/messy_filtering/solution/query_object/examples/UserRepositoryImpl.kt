package messy_filtering.solution.query_object.examples

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(
    private val em: EntityManager,
) : CustomUserRepository {
    override fun findByQuery(query: UserQuery): List<User> {
        val criteriaQuery = query.toCriteriaQuery(em)
        val typedQuery = em.createQuery(criteriaQuery)
        typedQuery.firstResult = query.page * query.size
        typedQuery.maxResults = query.size
        return typedQuery.resultList
    }
}
