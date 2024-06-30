package kr.tekit.lion.daongil.data.dto.remote.response.placeReview.get

import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class MyPlaceReview(
    val content: String,
    val date: LocalDate,
    val grade: Float,
    val imageList: List<String>,
    val nickname: String,
    val profileImg: String,
    val reviewId: Int
)