package kr.tekit.lion.daongil.data.dto.remote.response.naverMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coords(
    val center: Center?
)