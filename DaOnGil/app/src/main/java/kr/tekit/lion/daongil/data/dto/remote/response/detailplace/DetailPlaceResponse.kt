package kr.tekit.lion.daongil.data.dto.remote.response.detailplace

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.model.Review
import kr.tekit.lion.daongil.domain.model.SubDisability

@JsonClass(generateAdapter = true)
data class DetailPlaceResponse(
    val code: Int,
    val data: Data,
    val message: String
){
    fun toDomainModel(): PlaceDetailInfo{
        return PlaceDetailInfo(
            code = code,
            address = data.address,
            bookmarkNum = data.bookmarkNum,
            category = data.category,
            disability = data.disability,
            image = data.image,
            isMark = data.isMark,
            latitude = data.mapX,
            longitude = data.mapY,
            name = data.name,
            overview = data.overview,
            placeId = data.placeId,
            reviewList = data.reviewList?.map {
                Review(
                    reviewImg = it.reviewImg,
                    nickname = it.nickname,
                    profileImg = it.profileImg,
                    content = it.content,
                    reviewId = it.reviewId,
                    grade = it.grade,
                    date = it.date
                )
            },
            subDisability = data.subDisability?.map {
                SubDisability(
                    description = it.description,
                    subDisabilityName = it.subDisabilityName
                )
            }
        )
    }
}