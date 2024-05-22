package kr.tekit.lion.daongil.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.domain.model.KeywordSearch
import kr.tekit.lion.daongil.domain.repository.SearchKeywordRepository

class SearchByKeywordUseCase(
    private val searchKeywordRepository: SearchKeywordRepository
){
    suspend operator fun invoke(keyword: String): Flow<List<KeywordSearch>>{
         return searchKeywordRepository.searchByKeyword(keyword)
    }
}