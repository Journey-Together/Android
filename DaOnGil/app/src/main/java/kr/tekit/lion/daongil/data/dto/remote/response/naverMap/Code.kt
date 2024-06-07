package kr.tekit.lion.daongil.data.dto.remote.response.naverMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Code(
    val id: String?,
    val mappingId: String?,
    val type: String?
)