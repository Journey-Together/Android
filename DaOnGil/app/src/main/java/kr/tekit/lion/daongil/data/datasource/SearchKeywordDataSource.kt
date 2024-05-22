package kr.tekit.lion.daongil.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.tekit.lion.daongil.data.dto.remote.request.SearchKeywordRequest
import kr.tekit.lion.daongil.data.dto.remote.response.searchkeyword.SearchKeywordResponse
import kr.tekit.lion.daongil.data.network.service.KorWithService

class SearchKeywordDataSource(
    private val korWithService: KorWithService
) {
    suspend fun getSearchByKeywordResult(keyword: String): Flow<SearchKeywordResponse> {
        return flow {
            emit(
                korWithService.getSearchByKeywordResult(
                    SearchKeywordRequest(keyword).toRequestModel()
                )
            )
        }
    }
}