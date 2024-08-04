package kr.tekit.lion.daongil.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class MyPlaceReviewInfo (
    val content: String,
    val date: LocalDate,
    val grade: Float,
    val images: List<String>,
    val name: String,
    val placeId: Long,
    val reviewId: Long
) : Parcelable