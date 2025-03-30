package messy_filtering.problem.service

import messy_filtering.problem.UserRepository
import org.springframework.stereotype.Service

@Service
class UserAnalyticsService(
    private val userRepository: UserRepository,
) {
    fun getInactiveAdultUsers() {
        val users = userRepository.findByIsActiveAndAgeGreaterThan(false, 19)
        println("UserAnalyticsService.getInactiveAdultUsers: $users")
    }

    fun getUsersByLdap(ldap: String) {
        val users = userRepository.findByLdap(ldap)?.let { listOf(it) } ?: emptyList()
        println("UserAnalyticsService.getUsersByLdap: $users")
    }
}
