package kr.tekit.lion.daongil.data.dto.remote.response.plan

import kr.tekit.lion.daongil.domain.model.ScheduleDetailReview

data class ScheduleDetailReviewResponse(
    val code: Int,
    val data: Data,
    val message: String
){
    fun toDomainModel(): ScheduleDetailReview{
        return ScheduleDetailReview(
            reviewId = data.reviewId,
            content = data.content,
            grade = data.grade,
            imageList = data.imageList,
            isWriter = data.isWriter,
            hasReview = data.hasReview,
            profileUrl = data.profileUrl
        )
    }
}