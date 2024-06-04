package kr.tekit.lion.daongil.data.dto.remote.response.detailplace

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo

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
            reviewList = data.reviewList,
            subDisability = data.subDisability
        )
    }
}