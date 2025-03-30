package messy_filtering.solution.query_object.examples

import org.springframework.stereotype.Service

@Service
class UserAnalyticsService(
    private val userRepository: UserRepository,
) {
    fun getInactiveAdultUsers() {
        val query = UserQuery(isActive = false, minAge = 19)
        val users = userRepository.findByQuery(query)
        println("UserAnalyticsService.getInactiveAdultUsers: $users")
    }

    fun getUsersByLdap(ldap: String) {
        val query = UserQuery(ldap = ldap)
        val users = userRepository.findByQuery(query)
        val user = users.firstOrNull() // Assuming ldap is unique
        println("UserAnalyticsService.getUsersByLdap: $user")
    }
}

@Service
class UserManagementService(
    private val userRepository: UserRepository,
) {
    fun getActiveAdultUsers() {
        val query = UserQuery(isActive = true, minAge = 19)
        val users = userRepository.findByQuery(query)
        println("UserManagementService.getActiveAdultUsers: $users")
    }

    fun getActiveUsers() {
        val query = UserQuery(isActive = true)
        val users = userRepository.findByQuery(query)
        println("UserManagementService.getActiveUsers: $users")
    }

    fun getAllUsers() {
        val users = userRepository.findAll() // No filters needed
        println("UserManagementService.getAllUsers: $users")
    }

    fun getUserByLdap(ldap: String) {
        val query = UserQuery(ldap = ldap)
        val users = userRepository.findByQuery(query)
        val user = users.firstOrNull()
        println("UserManagementService.getUserByLdap: $user")
    }
}

@Service
class MarketingService(
    private val userRepository: UserRepository,
) {
    fun getEligibleUsersForAdultCampaign() {
        val query = UserQuery(isActive = true, minAge = 19)
        val users = userRepository.findByQuery(query)
        println("MarketingService.getEligibleUsersForAdultCampaign: $users")
    }
}
