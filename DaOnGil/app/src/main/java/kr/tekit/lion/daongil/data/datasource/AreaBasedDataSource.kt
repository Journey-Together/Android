package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.request.AreaBasedSearchRequest
import kr.tekit.lion.daongil.data.dto.remote.response.areabased.AreaBasedSearchResponse
import kr.tekit.lion.daongil.data.network.service.KorWithService

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