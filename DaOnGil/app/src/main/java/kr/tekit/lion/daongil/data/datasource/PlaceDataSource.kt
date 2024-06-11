package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.searchplace.SearchPlaceResponse
import kr.tekit.lion.daongil.data.network.service.PlaceService

class PlaceDataSource(
    private val placeService: PlaceService
) {
    suspend fun getPlaceDetailInfo(placeId: Long): DetailPlaceResponse {
        return placeService.getPlaceDetailInfo(placeId)
    }

    suspend fun searchPlace(request: Map<String, String?>): SearchPlaceResponse {
        return placeService.searchPlace(request)
    }
}