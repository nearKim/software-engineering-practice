package messy_filtering.problem.service

import messy_filtering.problem.UserRepository
import org.springframework.stereotype.Service

@Service
class UserManagementService(
    private val userRepository: UserRepository,
) {
    fun getActiveAdultUsers() {
        val users = userRepository.findByIsActiveAndAgeGreaterThan(true, 19)
        println("UserManagementService.getActiveAdultUsers: $users")
    }

    fun getActiveUsers() {
        val users = userRepository.findByIsActive(true)
        println("UserManagementService.getActiveUsers: $users")
    }

    fun getAllUsers() {
        val users = userRepository.findAll()
        println("UserManagementService.getAllUsers: $users")
    }

    fun getUserByLdap(ldap: String) {
        val user = userRepository.findByLdap(ldap)
        println("UserManagementService.getUserByLdap: $user")
    }
}
