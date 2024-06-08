package kr.tekit.lion.daongil.data.dto.remote.response.emergency.basic
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)