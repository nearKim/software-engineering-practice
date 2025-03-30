package messy_filtering.problem.service

import messy_filtering.problem.UserRepository
import org.springframework.stereotype.Service

@Service
class MarketingService(
    private val userRepository: UserRepository,
) {
    fun getEligibleUsersForAdultCampaign() {
        val users = userRepository.findByIsActiveAndAgeGreaterThan(true, 19)
        println("MarketingService.getEligibleUsersForAdultCampaign: $users")
    }
}
