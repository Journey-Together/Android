package kr.tekit.lion.daongil.data.dto.remote.response.review

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPlaceReviewDto(
    val address: String,
    val grade: Float,
    val image: String,
    val name: String,
    val reviewId: Long
)