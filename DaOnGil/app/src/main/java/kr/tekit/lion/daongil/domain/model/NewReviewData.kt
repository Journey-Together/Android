package kr.tekit.lion.daongil.domain.model

import java.time.LocalDate

data class NewReviewData(
    val date : LocalDate,
    val grade : Float,
    val content : String
)
