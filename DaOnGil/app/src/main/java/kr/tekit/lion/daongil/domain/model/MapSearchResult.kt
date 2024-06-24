package kr.tekit.lion.daongil.domain.model

data class MapSearchResult (
    val address: String,
    val name: String,
    val image: String,
    val disability: List<Int>,
    val placeId: Int,
    val latitude: Double,
    val longitude: Double
)