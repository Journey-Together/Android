package kr.tekit.lion.daongil.data.dto.remote.response.placeReview

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "myplaceReviewList")
    val myPlaceReviewList: List<MyPlaceReview>,
    val placeImg: String,
    val pageNo: Int?,
    val pageSize: Int?,
    val placeAddress: String,
    val placeName: String,
    val totalPages: Int?
)