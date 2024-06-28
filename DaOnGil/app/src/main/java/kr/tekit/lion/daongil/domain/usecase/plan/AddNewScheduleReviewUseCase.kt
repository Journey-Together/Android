package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.NewScheduleReview
import kr.tekit.lion.daongil.domain.model.ReviewImg
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class AddNewScheduleReviewUseCase (
    private val planRepository: PlanRepository
): BaseUseCase() {

    suspend operator fun invoke(
        planId: Long,
        scheduleReview: NewScheduleReview,
        images: List<ReviewImg>
    ): Result<Unit> = execute {
        planRepository.addNewScheduleReview(planId, scheduleReview, images)
    }
}