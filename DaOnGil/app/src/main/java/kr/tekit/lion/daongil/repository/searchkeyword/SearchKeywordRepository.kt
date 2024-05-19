package kr.tekit.lion.daongil.repository.searchkeyword

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.datasource.SearchKeywordDataSource
import kr.tekit.lion.daongil.model.KeywordSearch
import kr.tekit.lion.daongil.network.RetrofitInstance

interface SearchKeywordRepository {
    suspend fun searchByKeyword(keyword: String): Flow<List<KeywordSearch>>

    companion object {
        fun create(): SearchKeywordRepositoryImpl {
            return SearchKeywordRepositoryImpl(
                SearchKeywordDataSource(RetrofitInstance.korWithService)
            )
        }
    }
}