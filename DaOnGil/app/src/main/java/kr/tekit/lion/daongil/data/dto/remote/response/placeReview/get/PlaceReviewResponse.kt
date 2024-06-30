package kr.tekit.lion.daongil.data.dto.remote.response.placeReview.get

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.PlaceReview
import kr.tekit.lion.daongil.domain.model.PlaceReviewInfo

@JsonClass(generateAdapter = true)
data class PlaceReviewResponse(
    val code: Int,
    val data: Data,
    val message: String
) {
    fun toDomainModel(): PlaceReviewInfo {
        return PlaceReviewInfo(
            placeReviewList = data.myPlaceReviewList.map {
                PlaceReview(
                    content = it.content,
                    date = it.date,
                    grade = it.grade,
                    imageList = it.imageList,
                    nickname = it.nickname,
                    profileImg = it.profileImg,
                    reviewId = it.reviewId
                )
            },
            pageNo = data.pageNo,
            pageSize = data.pageSize,
            placeAddress = data.placeAddress,
            placeName = data.placeName,
            totalPages = data.totalPages
        )
    }
}