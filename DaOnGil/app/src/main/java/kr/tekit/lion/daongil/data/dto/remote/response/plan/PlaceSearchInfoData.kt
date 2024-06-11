package kr.tekit.lion.daongil.data.dto.remote.response.plan

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceSearchInfoData (
    val placeId: Long,
    val placeName: String,
    val category: String,
    val imageUrl: String,
)