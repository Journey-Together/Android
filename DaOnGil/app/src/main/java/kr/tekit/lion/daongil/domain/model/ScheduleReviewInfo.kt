package kr.tekit.lion.daongil.domain.model

data class ScheduleReviewInfo(
    val reviewId: Long,
    val content: String,
    val grade: Double,
    val imageList: List<String>?,
)
