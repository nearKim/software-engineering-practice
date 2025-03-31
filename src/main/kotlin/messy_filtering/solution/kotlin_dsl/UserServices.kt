package messy_filtering.solution.kotlin_dsl

import messy_filtering.solution.kotlin_dsl.domain.User
import org.springframework.stereotype.Service

@Service
class UserAnalyticsService(
    private val userRepository: UserRepository,
) {
    fun getUsersWithShippedOrders() {
        val users =
            userRepository.findByQuery({
                join(User::orders)
                where { cb, root, joins ->
                    val orderJoin = joins.getJoin(User::orders)
                    cb.equal(orderJoin.get<String>("status"), "shipped")
                }
            }, page = 0, size = 20)
        println("Users with shipped orders: $users")
    }

    fun getInactiveAdultUsers() {
        val users =
            userRepository.findByQuery(
                block = {
                    where { cb, root, joins ->
                        cb.and(
                            cb.equal(root.get<Boolean>("isActive"), false),
                            cb.greaterThan(root.get<Int>("age"), 19),
                        )
                    }
                },
                page = 0,
                size = 20,
            )
        println("UserAnalyticsService.getInactiveAdultUsers: $users")
    }

    fun getUsersByLdap(ldap: String) {
        val users =
            userRepository.findByQuery(
                block = {
                    where { cb, root, joins ->
                        cb.equal(root.get<String>("ldap"), ldap)
                    }
                },
            )
        println("UserAnalyticsService.getUsersByLdap: $users")
    }
}

/**
 * Leveraging extension functions
 */
@Service
class UserManagementService(
    private val userRepository: UserRepository,
) {
    fun getActiveAdultUsers() {
        val users =
            userRepository.findByQuery(
                block = {
                    where { cb, root, joins -> (User::isActive eq true)(cb, root) }
                    where { cb, root, joins -> (User::age gt 19)(cb, root) }
                    orderBy(asc(User::name))
                },
                page = 0,
                size = 10,
            )
        println("UserManagementService.getActiveAdultUsers: $users")
    }

    fun getAllActiveUsers() {
        val users =
            userRepository.findByQuery(
                block = {
                    where { cb, root, joins -> (User::isActive eq true)(cb, root) }
                },
            )
        println("UserManagementService.getAllActiveUsers: $users")
    }
}
