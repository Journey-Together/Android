package kr.tekit.lion.daongil.domain.model

data class PlaceDetailInfo (
    val code: Int,
    val address: String,
    var bookmarkNum: Int,
    val category: String,
    val disability: List<Int>,
    val image: String?,
    var isMark: Boolean,
    val latitude: String,
    val longitude: String,
    val name: String,
    val overview: String,
    val placeId: Long,
    val reviewList: List<Review>?,
    val subDisability: List<SubDisability>?
)