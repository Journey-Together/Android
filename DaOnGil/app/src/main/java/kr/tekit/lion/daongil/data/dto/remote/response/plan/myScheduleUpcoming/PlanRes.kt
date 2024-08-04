package kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleUpcoming

data class PlanRes(
    val planId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String?,
    val remainDate: String,
)
