package kr.tekit.lion.daongil.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.data.datasource.SearchKeywordDataSource
import kr.tekit.lion.daongil.domain.model.KeywordSearch
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.SearchKeywordRepositoryImpl

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