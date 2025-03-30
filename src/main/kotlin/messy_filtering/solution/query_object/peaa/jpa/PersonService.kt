package messy_filtering.solution.query_object.peaa.jpa

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * EntityManager Replaces Mapper, UnitOfWork, and DB
 * The Mapper executes queries and maps results, the UnitOfWork coordinates operations, and the DB handles raw database interactions.
 * JPA’s EntityManager takes over all these roles by managing the persistence context, executing queries, and handling transactions.
 */
@Service
@Transactional
class PersonService(
    private val em: EntityManager,
) {
    fun findPersonsWithDependentsGreaterThan(value: Int): List<Person> {
        val query = PersonQuery(minDependents = value)
        return executeQuery(query)
    }

    fun findPersonsByLastNamePattern(pattern: String): List<Person> {
        val query = PersonQuery(lastNamePattern = pattern)
        return executeQuery(query)
    }

    private fun executeQuery(query: PersonQuery): List<Person> {
        val criteriaQuery = query.toCriteriaQuery(em)
        val typedQuery = em.createQuery(criteriaQuery)
        typedQuery.firstResult = query.page * query.size
        typedQuery.maxResults = query.size
        return typedQuery.resultList
    }
}
