package kr.tekit.lion.daongil.domain.model

data class MyElapsedScheduleInfo(
    val planId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String,
    val hasReview: Boolean,
)
