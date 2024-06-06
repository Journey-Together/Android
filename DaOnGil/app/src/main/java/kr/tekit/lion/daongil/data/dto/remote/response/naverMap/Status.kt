package kr.tekit.lion.daongil.data.dto.remote.response.naverMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Status(
    val code: Int?,
    val message: String?,
    val name: String?
)