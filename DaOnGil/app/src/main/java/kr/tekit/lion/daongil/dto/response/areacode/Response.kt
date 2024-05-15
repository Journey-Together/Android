package kr.tekit.lion.daongil.dto.response.areacode

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.dto.response.Header

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)