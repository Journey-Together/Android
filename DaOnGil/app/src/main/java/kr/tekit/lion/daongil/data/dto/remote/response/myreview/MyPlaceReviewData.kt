package kr.tekit.lion.daongil.data.dto.remote.response.myreview

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MyPlaceReviewData(
    val myPlaceReviewDtoList: List<MyPlaceReviewDto>,
    val pageNo: Int,
    val pageSize: Int,
    val totalPages: Int
)