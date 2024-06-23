package kr.tekit.lion.daongil.domain.model

data class MyPlaceReview(
    val address: String,
    val grade: Float,
    val image: String,
    val name: String,
    val reviewId: Long
)