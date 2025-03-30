package messy_filtering.solution.query_object.jpa

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repository Replaces Mapper
 * Rather than a custom Mapper to handle SQL execution and result mapping,
 * Spring Data JPA provides a repository interface that abstracts these tasks.
 */
interface PersonRepository : JpaRepository<Person, Long>
