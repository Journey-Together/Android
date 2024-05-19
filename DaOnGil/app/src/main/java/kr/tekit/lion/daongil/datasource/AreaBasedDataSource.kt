package kr.tekit.lion.daongil.datasource

import kr.tekit.lion.daongil.dto.request.AreaBasedSearchRequest
import kr.tekit.lion.daongil.dto.response.areabased.AreaBasedSearchResponse
import kr.tekit.lion.daongil.network.service.KorWithService

class AreaBasedDataSource(
    private val korWithService: KorWithService
){
    suspend fun getSearchByAreaResult(
        contentTypeId: String,
        villageCode: String
    ): AreaBasedSearchResponse {
        return korWithService.getSearchByAreaResult(
            AreaBasedSearchRequest(contentTypeId, villageCode).toRequestModel()
        )
    }
}