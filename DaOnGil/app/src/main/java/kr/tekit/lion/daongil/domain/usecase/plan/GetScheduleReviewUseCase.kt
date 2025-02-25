package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.ScheduleReviewInfo
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetScheduleReviewInfoUseCase (
    private val planRepository: PlanRepository
): BaseUseCase() {
    suspend operator fun invoke(planId: Long): Result<ScheduleReviewInfo> = execute {
        val scheduleInfo = planRepository.getDetailScheduleInfo(planId)
        val detailReview = planRepository.getDetailScheduleReview(planId)

        ScheduleReviewInfo(
            title = scheduleInfo.title,
            startDate = scheduleInfo.startDate,
            endDate = scheduleInfo.endDate,
            imageUrl = scheduleInfo.images?.get(0) ?: "",
            reviewId = detailReview.reviewId ?: -1,
            content = detailReview.content ?: "",
            grade = detailReview.grade?.toFloat() ?: 0.0F,
            imageList = detailReview.imageList
        )
    }
}