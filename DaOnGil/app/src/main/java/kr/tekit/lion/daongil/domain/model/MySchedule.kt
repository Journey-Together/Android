package kr.tekit.lion.daongil.domain.model

data class MySchedule(
    val planId: Int,
    val title: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String,
    val remainDate: String?,
    val hasReview: Boolean?,
)