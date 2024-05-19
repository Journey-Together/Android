package kr.tekit.lion.daongil.repository.searchkeyword

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.tekit.lion.daongil.datasource.SearchKeywordDataSource
import kr.tekit.lion.daongil.model.KeywordSearch

class SearchKeywordRepositoryImpl(
    private val searchKeywordDataSource: SearchKeywordDataSource
) : SearchKeywordRepository {
    override suspend fun searchByKeyword(keyword: String): Flow<List<KeywordSearch>> {
        return searchKeywordDataSource.getSearchByKeywordResult(keyword).map {
                it.toDomainModel()
        }
    }
}