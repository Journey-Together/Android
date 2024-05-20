package kr.tekit.lion.daongil.dto.response.areabased

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.model.KeywordSearch

@JsonClass(generateAdapter = true)
data class AreaBasedSearchResponse(
    @Json(name = "response")
    val response: Response
){
    fun toDomainModel(): List<KeywordSearch> {
        return response.body.items.item.map {
            KeywordSearch(
                title = it.title,
                address = it.address,
                detailAddress = it.detailAddress,
                contentId = it.contentId,
                contentTypeId = it.contentTypeId,
                firstImageUrl = it.firstImage,
                latitude = it.mapX,
                longitude = it.mapY
            )
        }
    }
}