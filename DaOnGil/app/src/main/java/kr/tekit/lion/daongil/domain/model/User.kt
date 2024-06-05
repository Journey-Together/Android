package kr.tekit.lion.daongil.domain.model

data class User (
    val memberId: Int,
    val email: String = "",
    val name: String = "",
    val profileUuid: String = "",
)