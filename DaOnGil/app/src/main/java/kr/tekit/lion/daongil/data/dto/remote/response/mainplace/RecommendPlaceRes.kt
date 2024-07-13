package kr.tekit.lion.daongil.data.dto.remote.response.mainplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecommendPlaceRes(
    val address: String,
    val disability: List<String>,
    val image: String,
    val name: String,
    val placeId: Long
)