package kr.tekit.lion.daongil.domain.model

data class PlaceDetailInfoGuest (
    val code: Int,
    val address: String,
    val bookmarkNum: Int,
    val category: String,
    val disability: List<Int>,
    val image: String?,
    val latitude: String,
    val longitude: String,
    val name: String,
    val overview: String,
    val placeId: Int,
    val reviewList: List<Review>?,
    val subDisability: List<SubDisability>?
)