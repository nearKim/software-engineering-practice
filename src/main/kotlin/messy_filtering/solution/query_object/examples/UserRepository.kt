package messy_filtering.solution.query_object.examples

import org.springframework.data.jpa.repository.JpaRepository

// Main repository interface
interface UserRepository :
    JpaRepository<User, Int>,
    CustomUserRepository

// Custom repository interface
interface CustomUserRepository {
    fun findByQuery(query: UserQuery): List<User>
}
