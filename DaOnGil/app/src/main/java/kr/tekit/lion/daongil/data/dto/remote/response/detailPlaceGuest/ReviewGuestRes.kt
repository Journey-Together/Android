package kr.tekit.lion.daongil.data.dto.remote.response.detailPlaceGuest

import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class ReviewGuestRes (
    val reviewId: Long,
    val nickname : String,
    val profileImg : String,
    val content : String,
    val reviewImg : String,
    val grade : Float,
    val date : LocalDateTime
)