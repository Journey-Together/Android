package kr.tekit.lion.daongil.domain.model

import java.time.LocalDate

data class PlaceReview (
    val content: String,
    val date: LocalDate,
    val grade: Float,
    val imageList: List<String>,
    val nickname: String,
    val profileImg: String,
    val reviewId: Int
)