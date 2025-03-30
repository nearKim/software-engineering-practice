package messy_filtering.solution.dynamic_specification.service

import messy_filtering.solution.dynamic_specification.specification.LogicalOperator
import messy_filtering.solution.dynamic_specification.specification.SpecificationImpl
import messy_filtering.solution.dynamic_specification.User
import messy_filtering.solution.dynamic_specification.UserRepository
import org.springframework.stereotype.Service

@Service
class UserManagementService(
    private val userRepository: UserRepository,
) {
    fun getActiveAdultUsers() {
        val isActive = SpecificationImpl.simple<User> { it.isActive }
        val isAdult = SpecificationImpl.simple<User> { it.age > 19 }
        val spec = SpecificationImpl.composite(LogicalOperator.AND, isActive, isAdult)
        val users = userRepository.findAll(spec)
        println("UserManagementService.getActiveAdultUsers: $users")
    }

    fun getActiveUsers() {
        val spec = SpecificationImpl.simple<User> { it.isActive }
        val users = userRepository.findAll(spec)
        println("UserManagementService.getActiveUsers: $users")
    }

    fun getAllUsers() {
        val users = userRepository.findAll()
        println("UserManagementService.getAllUsers: $users")
    }

    fun getUserByLdap(ldap: String) {
        val spec = SpecificationImpl.simple<User> { it.ldap == ldap }
        val users = userRepository.findAll(spec)
        println("UserManagementService.getUserByLdap: $users")
    }
}
