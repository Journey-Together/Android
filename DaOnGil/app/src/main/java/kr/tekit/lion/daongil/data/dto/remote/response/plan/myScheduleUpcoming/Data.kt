package kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleUpcoming

data class Data(
    val planResList: List<PlanRes>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int,
    val last: Boolean,
)
