package kr.tekit.lion.daongil.data.dto.remote.response.mainplace

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.PlaceMainInfo
import kr.tekit.lion.daongil.domain.model.AroundPlace
import kr.tekit.lion.daongil.domain.model.RecommendPlace

@JsonClass(generateAdapter = true)
data class MainPlaceResponse(
    val code: Int,
    val data: Data,
    val message: String
) {
    fun toDomainModel(): PlaceMainInfo {
        return PlaceMainInfo(
            aroundPlaceList = data.aroundPlaceList.map {
                AroundPlace(
                    address = it.address,
                    disability = it.disability,
                    image = it.image,
                    name = it.name,
                    placeId = it.placeId)
            },
            recommendPlaceList = data.recommendPlaceList.map {
                RecommendPlace(
                    address = it.address,
                    disability = it.disability,
                    image = it.image,
                    name = it.name,
                    placeId = it.placeId
                )
            }
        )
    }

}