package kr.tekit.lion.daongil.domain.model

data class PublicSchedule(
    val planId: Int,
    val title: String,
    val imageUrl: String,
    val memberId: Int,
    val memberNickname: String,
    val memberImageUrl: String,
    val date: String,
)
