package kr.tekit.lion.daongil.dto.response.areacode

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "rnum")
    val rNum: Int,
    val code: String,
    val name: String
)