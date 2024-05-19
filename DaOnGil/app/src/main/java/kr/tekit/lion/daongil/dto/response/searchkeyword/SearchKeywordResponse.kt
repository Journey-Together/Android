package kr.tekit.lion.daongil.dto.response.searchkeyword

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.model.KeywordSearch

@JsonClass(generateAdapter = true)
data class SearchKeywordResponse(
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