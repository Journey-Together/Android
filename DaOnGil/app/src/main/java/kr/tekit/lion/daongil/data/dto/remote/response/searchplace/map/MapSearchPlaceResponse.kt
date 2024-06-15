package kr.tekit.lion.daongil.data.dto.remote.response.searchplace.map

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.MapSearchResult

@JsonClass(generateAdapter = true)
data class MapSearchPlaceResponse(
    val code: Int,
    val data: Data,
    val message: String
){
    fun toDomainModel(): List<MapSearchResult>{
        return data.placeResList.map{
            MapSearchResult(
                address = it.address,
                disability = it.disability,
                image = it.image,
                name = it.name,
                latitude = it.mapX,
                longitude = it.mapY,
                placeId = it.placeId
            )
        }
    }

}

