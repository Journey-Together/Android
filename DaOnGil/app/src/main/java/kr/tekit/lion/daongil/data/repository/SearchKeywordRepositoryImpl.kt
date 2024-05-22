package kr.tekit.lion.daongil.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.tekit.lion.daongil.data.datasource.SearchKeywordDataSource
import kr.tekit.lion.daongil.domain.model.KeywordSearch
import kr.tekit.lion.daongil.domain.repository.SearchKeywordRepository

class SearchKeywordRepositoryImpl(
    private val searchKeywordDataSource: SearchKeywordDataSource
) : SearchKeywordRepository {
    override suspend fun searchByKeyword(keyword: String): Flow<List<KeywordSearch>> {
        return searchKeywordDataSource.getSearchByKeywordResult(keyword).map {
                it.toDomainModel()
        }
    }
}