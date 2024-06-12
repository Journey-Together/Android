package kr.tekit.lion.daongil.domain.model

data class SearchPlace (
    val address: String,
    val disability: List<Int>,
    val image: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val placeId: Int
)