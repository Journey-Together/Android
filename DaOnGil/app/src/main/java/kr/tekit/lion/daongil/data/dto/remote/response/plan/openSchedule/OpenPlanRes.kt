package kr.tekit.lion.daongil.data.dto.remote.response.plan.openSchedule


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenPlanRes(
    val date: String,
    val imageUrl: String?,
    val memberId: Int,
    val memberImageUrl: String,
    val memberNickname: String,
    val planId: Int,
    val title: String
)