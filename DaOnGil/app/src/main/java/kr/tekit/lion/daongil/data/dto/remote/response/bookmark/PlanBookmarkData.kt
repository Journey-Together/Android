package kr.tekit.lion.daongil.data.dto.remote.response.bookmark

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanBookmarkData(
    val image: Any,
    val name: String,
    val planId: Int,
    val profileImg: String,
    val title: String
)