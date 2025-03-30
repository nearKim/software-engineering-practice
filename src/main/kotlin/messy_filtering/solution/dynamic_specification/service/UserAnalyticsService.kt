package messy_filtering.solution.dynamic_specification.service

import messy_filtering.solution.dynamic_specification.UserRepository
import messy_filtering.solution.dynamic_specification.specification.UserSpecifications
import org.springframework.stereotype.Service

@Service
class UserAnalyticsService(
    private val userRepository: UserRepository,
) {
    fun getActiveAdultUsers() {
        val spec = UserSpecifications.activeAdult()
        val users = userRepository.findAll(spec)
        println("Active adult users: $users")
    }

    fun getUsersByLdap(ldap: String) {
        val spec = UserSpecifications.ldapEquals(ldap)
        val users = userRepository.findAll(spec)
        println("Users with LDAP $ldap: $users")
    }
}
