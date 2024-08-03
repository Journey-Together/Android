package kr.tekit.lion.daongil.data.dto.remote.response.plan.myScheduleElapsed

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanRes(
    val planId: Long,
    val title: String,
    val startDate: String,
    val endDate: String,
    val imageUrl: String?,
    val hasReview: Boolean,
)
