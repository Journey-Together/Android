package kr.tekit.lion.daongil.domain.model

data class PlaceReviewInfo (
    val placeReviewList: List<PlaceReview>,
    val placeImg: String,
    val pageNo: Int?,
    val pageSize: Int?,
    val placeAddress: String,
    val placeName: String,
    val totalPages: Int?
)


