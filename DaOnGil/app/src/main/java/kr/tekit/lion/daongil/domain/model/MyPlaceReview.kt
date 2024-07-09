package kr.tekit.lion.daongil.domain.model

data class MyPlaceReview(
    val myPlaceReviewInfoList: List<MyPlaceReviewInfo>,
    val pageNo: Int,
    val pageSize: Int,
    val reviewNum: Long,
    val totalPages: Int
)