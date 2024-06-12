package kr.tekit.lion.daongil.data.dto.remote.response.plan.openSchedule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "last")
    val last: Boolean,
    @Json(name = "openPlanResList")
    val openPlanResList: List<OpenPlanRes>,
    @Json(name = "pageNo")
    val pageNo: Int,
    @Json(name = "pageSize")
    val pageSize: Int,
    @Json(name = "totalPages")
    val totalPages: Int
)