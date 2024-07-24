package kr.tekit.lion.daongil.data.dto.remote.response.plan

import com.squareup.moshi.JsonClass
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfoList.TotalElementsInfo
import kr.tekit.lion.daongil.domain.model.PlaceSearchInfoList.PlaceSearchInfo
import kr.tekit.lion.daongil.domain.model.PlaceSearchResult

@JsonClass(generateAdapter = true)
data class PlaceSearchResultsResponse (
    val code: Int,
    val message: String,
    val data: PlaceSearchResultsData
){
    fun toDomainModel() : PlaceSearchResult {
        return PlaceSearchResult(
            placeInfoList = this.data.placeInfoList.map {
                PlaceSearchInfo(
                    placeId = it.placeId,
                    placeName = it.placeName,
                    category = it.category,
                    imageUrl = it.imageUrl
                )
            },
            pageNo = this.data.pageNo,
            last = this.data.last,
            TotalElementsInfo(
                totalElements = this.data.totalElements
            )
        )
    }
}