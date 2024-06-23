package kr.tekit.lion.daongil.data.dto.remote.response.plan.myMainSchedule

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val endDate: String,
    val hasReview: Boolean?,
    val imageUrl: String?,
    val planId: Long,
    val remainDate: String?,
    val startDate: String?,
    val title: String?
)