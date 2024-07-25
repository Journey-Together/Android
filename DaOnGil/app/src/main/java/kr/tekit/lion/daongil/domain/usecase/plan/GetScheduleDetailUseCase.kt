package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.domain.model.ScheduleDetailnfo
import kr.tekit.lion.daongil.domain.model.ScheduleDetailReview
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result
import kr.tekit.lion.daongil.domain.usecase.base.combineResults


class GetScheduleDetailUseCase(
    private val getScheduleDetailReviewUseCase: GetScheduleDetailReviewUseCase,
    private val getScheduleDetailnfoUseCase: GetScheduleDetailnfoUseCase
): BaseUseCase() {
    suspend operator fun invoke(planId: Long): Result<ScheduleDetail> = execute {
        val scheduleInfoResult = getScheduleDetailnfoUseCase(planId)
        val scheduleReviewResult = getScheduleDetailReviewUseCase(planId)

        val combinedResult = combineResults(scheduleInfoResult, scheduleReviewResult) { scheduleInfo, scheduleReview ->
            combineScheduleDetail(scheduleInfo, scheduleReview)
        }

        when (combinedResult) {
            is Result.Success -> combinedResult.value
            is Result.Error -> throw combinedResult.error
        }

    }
}


private fun combineScheduleDetail(
    info: ScheduleDetailnfo,
    review: ScheduleDetailReview
): ScheduleDetail {
    return ScheduleDetail(
        title = info.title,
        startDate = info.startDate,
        endDate = info.endDate,
        remainDate = info.remainDate,
        isPublic = info.isPublic,
        isWriter = info.isWriter,
        nickname = info.nickname,
        images = info.images,
        dailyPlans = info.dailyPlans,
        writerId = info.writerId,
        reviewId = review.reviewId,
        content = review.content,
        grade = review.grade,
        reviewImages = review.imageList,
        hasReview = review.hasReview,
        profileUrl = review.profileUrl
    )
}

