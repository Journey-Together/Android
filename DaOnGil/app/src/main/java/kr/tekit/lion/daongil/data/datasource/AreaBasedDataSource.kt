package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.request.AreaBasedSearchRequest
import kr.tekit.lion.daongil.data.dto.remote.response.areabased.AreaBasedSearchResponse
import kr.tekit.lion.daongil.data.network.service.KorWithService

class AreaBasedDataSource(
    private val korWithService: KorWithService
) {
    suspend fun getSearchByAreaResult(contentId: String): AreaBasedSearchResponse {
        return korWithService.getAreaBasedResult(AreaBasedSearchRequest(contentId).toRequestModel())
    }
}