package messy_filtering.solution.dynamic_specification.service

import messy_filtering.solution.dynamic_specification.specification.LogicalOperator
import messy_filtering.solution.dynamic_specification.specification.SpecificationImpl
import messy_filtering.solution.dynamic_specification.User
import messy_filtering.solution.dynamic_specification.UserRepository
import org.springframework.stereotype.Service

@Service
class MarketingService(
    private val userRepository: UserRepository,
) {
    fun getEligibleUsersForAdultCampaign() {
        val isActive = SpecificationImpl.simple<User> { it.isActive }
        val isAdult = SpecificationImpl.simple<User> { it.age > 19 }
        val spec = SpecificationImpl.composite(LogicalOperator.AND, isActive, isAdult)
        val users = userRepository.findAll(spec)
        println("MarketingService.getEligibleUsersForAdultCampaign: $users")
    }
}
