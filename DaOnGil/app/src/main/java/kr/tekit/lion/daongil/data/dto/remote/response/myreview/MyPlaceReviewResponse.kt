package kr.tekit.lion.daongil.data.dto.remote.response.myreview

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MyPlaceReview
import kr.tekit.lion.daongil.domain.model.MyPlaceReviewInfo
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class MyPlaceReviewResponse(
    val code: Int,
    val data: MyPlaceReviewData,
    val message: String
) {
    fun toDomainModel(): MyPlaceReview {
        return MyPlaceReview(
            myPlaceReviewInfoList = data.myPlaceReviewDtoList.map {
                MyPlaceReviewInfo(
                    content = it.content,
                    date = it.date,
                    grade = it.grade,
                    images = it.images,
                    name = it.name,
                    placeId = it.placeId,
                    reviewId = it.placeId
                )
            },
            pageNo = data.pageNo,
            pageSize = data.pageSize,
            reviewNum = data.reviewNum,
            totalPages = data.totalPages
        )
    }
}