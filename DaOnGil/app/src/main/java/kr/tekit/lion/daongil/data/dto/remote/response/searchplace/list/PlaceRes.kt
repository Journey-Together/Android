package kr.tekit.lion.daongil.data.dto.remote.response.searchplace.list

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlaceRes(
    val address: String,
    val disability: List<String>,
    val image: String,
    val mapX: String,
    val mapY: String,
    val name: String,
    val placeId: Long
)