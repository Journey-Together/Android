package kr.tekit.lion.daongil.domain.model

data class MyUpcomingScheduleInfo(
    val planId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String,
    val remainDate: String,
)
