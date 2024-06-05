package kr.tekit.lion.daongil.data.dto.remote.response.login


import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.data.dto.remote.response.login.Data

@JsonClass(generateAdapter = true)
data class UserLoginResponse(
    val code: Int,
    val data: Data,
    val message: String
    // 로그인 타입은 String으로 보낼 예정.
)