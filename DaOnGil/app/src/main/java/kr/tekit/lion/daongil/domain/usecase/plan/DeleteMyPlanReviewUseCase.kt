package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.ScheduleDetailReview
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class DeleteMyPlanReviewUseCase(
    private val planRepository: PlanRepository
): BaseUseCase() {
    suspend operator fun invoke(reviewId: Long, planId: Long): Result<ScheduleDetailReview> = execute {
        planRepository.deleteMyPlanReview(reviewId)
        planRepository.getDetailScheduleReview(planId)
    }

}