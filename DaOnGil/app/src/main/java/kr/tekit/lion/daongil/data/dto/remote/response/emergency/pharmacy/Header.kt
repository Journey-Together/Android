package kr.tekit.lion.daongil.data.dto.remote.response.emergency.pharmacy


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Header(
    @Json(name = "resultCode")
    val resultCode: String,
    @Json(name = "resultMsg")
    val resultMsg: String?
)