package kr.tekit.lion.daongil.data.dto.remote.response.searchplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val pageNo: Int,
    val pageSize: Int,
    val placeResList: List<PlaceRes>,
    val totalPages: Int
)