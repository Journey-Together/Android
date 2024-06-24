package kr.tekit.lion.daongil.data.dto.remote.response.searchplace.map

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val placeResList: List<PlaceRes>
)