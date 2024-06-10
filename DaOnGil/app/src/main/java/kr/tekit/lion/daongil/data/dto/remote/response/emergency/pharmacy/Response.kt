package kr.tekit.lion.daongil.data.dto.remote.response.emergency.pharmacy


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    @Json(name = "body")
    val body: Body,
    @Json(name = "header")
    val header: Header
)