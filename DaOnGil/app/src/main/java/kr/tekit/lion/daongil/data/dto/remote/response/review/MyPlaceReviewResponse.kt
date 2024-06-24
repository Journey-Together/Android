package kr.tekit.lion.daongil.data.dto.remote.response.review

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyPlaceReview

@JsonClass(generateAdapter = true)
data class MyPlaceReviewResponse(
    val code: Int,
    val data: MyPlaceReviewData,
    val message: String
) {
    fun toDomainModel(): List<MyPlaceReview> {
        return data.myPlaceReviewDtoList.map {
            MyPlaceReview(
                address = it.address,
                grade = it.grade,
                image = it.image,
                name = it.name,
                reviewId = it.reviewId
            )
        }
    }
}