package kr.tekit.lion.daongil.data.dto.remote.response.member

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyDefaultInfoData(
    val date: Int,
    val name: String,
    val profileImg: String,
    val reviewNum: Int
)