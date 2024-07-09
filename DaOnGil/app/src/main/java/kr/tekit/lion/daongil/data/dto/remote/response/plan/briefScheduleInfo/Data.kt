package kr.tekit.lion.daongil.data.dto.remote.response.plan.briefScheduleInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val planId: Long,
    val title: String?,
    val startDate: String?,
    val endDate: String?,
    val imageUrl: String?,
)