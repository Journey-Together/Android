package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.mainplace.MainPlaceResponse
import kr.tekit.lion.daongil.data.network.service.PlaceService

class PlaceMainInfoDataSource (
    private val placeService : PlaceService
) {
    suspend fun getPlaceMainInfo(areaCode : String, sigunguCode : String) : MainPlaceResponse {
        return placeService.getPlaceMainInfo(areaCode, sigunguCode)
    }
}