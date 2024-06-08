package kr.tekit.lion.daongil.data.dto.remote.response.emergency.aed

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Header(
    val resultCode: String,
    val resultMsg: String
)