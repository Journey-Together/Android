package kr.tekit.lion.daongil.data.dto.remote.request

data class ScheduleReviewDetailRequest(
    val grade: Float,
    val content: String,
    val isPublic: Boolean
)