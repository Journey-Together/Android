package kr.tekit.lion.daongil.domain.model

data class PlaceSearchInfo(
    val placeId: Long,
    val placeName: String,
    val category: String,
    val imageUrl: String?,
)
