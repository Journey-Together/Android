package kr.tekit.lion.daongil.data.dto.remote.response.login


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val accessToken: String,
    val email: String,
    val loginType: String,
    val memberId: Int,
    val memberType: String,
    val name: String,
    val profileUuid: String,
    val refreshToken: String
)