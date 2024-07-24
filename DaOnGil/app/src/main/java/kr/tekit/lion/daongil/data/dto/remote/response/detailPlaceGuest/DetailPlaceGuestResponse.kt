package kr.tekit.lion.daongil.data.dto.remote.response.detailPlaceGuest

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfoGuest
import kr.tekit.lion.daongil.domain.model.Review
import kr.tekit.lion.daongil.domain.model.SubDisability

@JsonClass(generateAdapter = true)
data class DetailPlaceGuestResponse(
    val code: Int,
    val data: Data,
    val message: String
) {
    fun toDomainModel(): PlaceDetailInfoGuest{
        return PlaceDetailInfoGuest(
            code = code,
            address = data.address,
            bookmarkNum = data.bookmarkNum,
            category = data.category,
            disability = data.disability,
            image = data.image,
            latitude = data.mapX,
            longitude = data.mapY,
            name = data.name,
            overview = data.overview,
            tel = data.tel.orEmpty(),
            homepage = data.homepage.orEmpty(),
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