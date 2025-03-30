package messy_filtering.solution.dynamic_specification

import messy_filtering.solution.dynamic_specification.specification.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findAll(spec: Specification<User>): List<User> = findAll().filter { spec.isSatisfiedBy(it) }
}

// FIXME: spring-data-jpa의 경우 Specification이 이미 존재한다.
// https://docs.spring.io/spring-data/jpa/reference/jpa/specifications.html
// import org.springframework.data.jpa.repository.JpaSpecificationExecutor
//
// @Repository
// interface UserRepository : JpaRepository<User, Int>, JpaSpecificationExecutor<User>
