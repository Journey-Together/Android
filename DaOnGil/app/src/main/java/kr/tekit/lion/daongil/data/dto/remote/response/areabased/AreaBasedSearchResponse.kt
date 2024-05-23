package kr.tekit.lion.daongil.data.dto.remote.response.areabased

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.AreaBased
import kr.tekit.lion.daongil.domain.model.KeywordSearch

@JsonClass(generateAdapter = true)
data class AreaBasedSearchResponse(
    @Json(name = "response")
    val response: Response
){
    fun toDomainModel(): List<AreaBased> {
        return response.body.items.item.map {
            AreaBased(
                areaCode = it.areaCode,
                address = it.address,
                siGunGuCode = it.siGunGuCode,
                detailAddress = it.detailAddress,
                categoryCode1 = it.cat1,
                categoryCode2 = it.cat2,
                categoryCode3 = it.cat3,
                contentId = it.contentId,
                contentTypeId = it.contentTypeId,
                mainImageUrl = it.firstImage,
                thumbnailImageUrl = it.firstImage2,
                latitude = it.mapX,
                longitude = it.mapY,
                tel = it.tel,
                title = it.title,
                )
        }
    }
}