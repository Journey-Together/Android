package kr.tekit.lion.daongil.domain.model

data class KeywordSearch(
    val title: String,
    val address: String,
    val detailAddress: String,
    val contentId:String,
    val contentTypeId: String,
    val firstImageUrl: String,
    val latitude: String,
    val longitude: String,
)