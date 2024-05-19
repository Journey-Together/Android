package kr.tekit.lion.daongil.usecase

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.model.KeywordSearch
import kr.tekit.lion.daongil.repository.searchkeyword.SearchKeywordRepository

class SearchByKeywordUseCase(
    private val searchKeywordRepository: SearchKeywordRepository
){
    suspend operator fun invoke(keyword: String): Flow<List<KeywordSearch>>{
         return searchKeywordRepository.searchByKeyword(keyword)
    }
}