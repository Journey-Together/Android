package kr.tekit.lion.daongil.domain.model

data class ScheduleReviewInfo(
    // 여행 일정 정보
    val title: String,
    val startDate: String,
    val endDate: String,
    val isPublic: Boolean,
    val imageUrl: String,
    // 리뷰 정보
    val reviewId: Long,
    val content: String,
    val grade: Float,
    val imageList: List<String>?,
)
