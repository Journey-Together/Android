package kr.tekit.lion.daongil.domain.model

data class AreaBased (
    val areaCode: String,
    val address: String,
    val siGunGuCode: String,
    val detailAddress: String,
    val categoryCode1: String,
    val categoryCode2: String,
    val categoryCode3: String,
    val contentId: String,
    val contentTypeId: String,
    val mainImageUrl: String,
    val thumbnailImageUrl: String,
    val latitude: String,
    val longitude: String,
    val tel: String,
    val title: String
)