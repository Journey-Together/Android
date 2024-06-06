package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.naverMap.ReverseGecodeResponse
import kr.tekit.lion.daongil.data.network.service.NaverMapService

class NaverMapDataSource(
    private val naverMapService: NaverMapService
) {
    suspend fun getReverseGeoCode(coords: String) : ReverseGecodeResponse {
        return naverMapService.getReverseGeoCode(coords)
    }
}