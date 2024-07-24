package kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleElapsed

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data (
    val planResList: List<PlanRes>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int,
    val last: Boolean,
)