package kr.tekit.lion.daongil.data.dto.remote.response.mainplace

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val aroundPlaceList: List<AroundPlaceRes>,
    val recommendPlaceList: List<RecommendPlaceRes>
)