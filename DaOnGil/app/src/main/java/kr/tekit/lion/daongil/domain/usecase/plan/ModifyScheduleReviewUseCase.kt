package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.ModifiedScheduleReview
import kr.tekit.lion.daongil.domain.model.ReviewImage
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class ModifyScheduleReviewUseCase (
    private val planRepository: PlanRepository
): BaseUseCase() {
    suspend operator fun invoke(
        reviewId: Long,
        scheduleReview: ModifiedScheduleReview,
        images: List<ReviewImage>
    ): Result<Unit> = execute {
        planRepository.modifyScheduleReview(reviewId, scheduleReview, images)
    }
}