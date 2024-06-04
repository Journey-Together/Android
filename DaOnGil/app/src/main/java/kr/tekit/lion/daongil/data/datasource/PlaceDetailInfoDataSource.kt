package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import kr.tekit.lion.daongil.data.network.service.PlaceService

class PlaceDetailInfoDataSource(
    private val placeService: PlaceService
) {
    suspend fun getPlaceDetailInfo(placeId: Long): DetailPlaceResponse {
        return placeService.getPlaceDetailInfo(placeId)
    }
}