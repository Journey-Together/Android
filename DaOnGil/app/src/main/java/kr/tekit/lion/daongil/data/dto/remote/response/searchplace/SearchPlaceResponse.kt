package kr.tekit.lion.daongil.data.dto.remote.response.searchplace

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.SearchPlace

@JsonClass(generateAdapter = true)
data class SearchPlaceResponse(
    val code: Int,
    val data: Data,
    val message: String
)

fun SearchPlaceResponse.toDomainModel(): List<SearchPlace> {
    return data.placeResList.map {
        SearchPlace(
            address = it.address,
            disability = it.disability,
            image = it.image,
            latitude = it.mapX,
            longitude = it.mapY,
            name = it.name,
            placeId = it.placeId
        )
    }
}


