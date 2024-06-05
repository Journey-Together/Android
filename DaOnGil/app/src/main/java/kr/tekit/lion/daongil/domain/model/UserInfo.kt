package kr.tekit.lion.daongil.domain.model

data class UserInfo(
    val memberId: Int,
    val email: String,
    val name: String,
    val profileUuid: String,
    val loginType: String,
    val accessToken: String,
    val refreshToken: String,
    )
