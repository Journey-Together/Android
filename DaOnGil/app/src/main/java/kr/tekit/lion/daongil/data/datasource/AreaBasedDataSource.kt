package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.base.ApiConstants.Companion.areaBasedSearchRequest
import kr.tekit.lion.daongil.data.dto.remote.response.areabased.AreaBasedSearchResponse
import kr.tekit.lion.daongil.data.network.service.KorWithService

class AreaBasedDataSource(
    private val korWithService: KorWithService
) {
    suspend fun getSearchByAreaResult(): AreaBasedSearchResponse {
        return korWithService.getAreaBasedResult(areaBasedSearchRequest)
    }
}