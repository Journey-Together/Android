package kr.tekit.lion.daongil.domain.model

data class PlaceBookmark(
    val address: String,
    val disability: List<Int>,
    val image: String,
    val name: String,
    val placeId: Int
)