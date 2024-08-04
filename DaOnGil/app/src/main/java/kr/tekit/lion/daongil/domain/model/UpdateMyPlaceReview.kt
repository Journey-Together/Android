package kr.tekit.lion.daongil.domain.model

import java.time.LocalDate

data class UpdateMyPlaceReview(
    val grade: Float,
    val date: LocalDate,
    val content: String,
    val deleteImgUrls: List<String>
)