package kr.tekit.lion.daongil.domain.model

data class User (
    val memberId: Int = 0,
    val email: String = "",
    val name: String = "",
    val profileUuid: String = "",
)