package kr.tekit.lion.daongil.data.dto.remote.response.barrierfree

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.data.dto.remote.base.Header

@JsonClass(generateAdapter = true)
data class Response(
    val body: Body,
    val header: Header
)