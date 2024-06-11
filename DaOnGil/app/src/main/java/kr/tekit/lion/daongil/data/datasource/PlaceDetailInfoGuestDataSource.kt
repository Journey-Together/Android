package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.detailPlaceGuest.DetailPlaceGuestResponse
import kr.tekit.lion.daongil.data.network.service.PlaceService

class PlaceDetailInfoGuestDataSource(
    private val placeService: PlaceService
) {
    suspend fun getPlaceDetailInfoGuest(placeId: Long):DetailPlaceGuestResponse{
        return placeService.getPlaceDetailInfoGuest(placeId)
    }
}