package kr.tekit.lion.daongil.domain.model

data class OpenPlanInfo (
    val date: String,
    val imageUrl: String,
    val memberId: Int,
    val memberImageUrl: String,
    val memberNickname: String,
    val planId: Int,
    val title: String
)