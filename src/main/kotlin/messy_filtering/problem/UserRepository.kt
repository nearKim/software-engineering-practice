package messy_filtering.problem

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByIsActive(isActive: Boolean): List<User>

    fun findByLdap(ldap: String): User?

    fun findByIsActiveAndAgeGreaterThan(
        isActive: Boolean,
        age: Int,
    ): List<User>
}
