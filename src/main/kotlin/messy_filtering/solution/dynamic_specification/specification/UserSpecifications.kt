package messy_filtering.solution.dynamic_specification.specification

import messy_filtering.solution.dynamic_specification.User

/**
 * Services like UserAnalyticsService depend on UserSpecifications, a concrete object, rather than an abstraction.
 * Ideally, DIP prefers dependencies on interfaces, but since UserSpecifications is a stateless utility providing factory methods (similar to a static class), this dependency is manageable.
 * If it were to grow in complexity (e.g., requiring its own dependencies), it could be refactored into an injectable service with an interface.
 * For now, its simplicity makes this unnecessary.
 */
object UserSpecifications {
    fun isActive(): Specification<User> = SpecificationImpl.simple { it.isActive }

    fun isAdult(): Specification<User> = SpecificationImpl.simple { it.age > 19 }

    fun ldapEquals(ldap: String): Specification<User> = SpecificationImpl.simple { it.ldap == ldap }

    // Example of a composite specification
    fun activeAdult(): Specification<User> =
        SpecificationImpl.simple { user ->
            isActive().isSatisfiedBy(user) && isAdult().isSatisfiedBy(user)
        }
}
