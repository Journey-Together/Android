package kr.tekit.lion.daongil.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.tekit.lion.daongil.data.datasource.RecentSearchKeywordDataSource
import kr.tekit.lion.daongil.data.dto.local.toDomainModel
import kr.tekit.lion.daongil.domain.model.RecentSearchKeyword
import kr.tekit.lion.daongil.domain.repository.RecentSearchKeywordRepository

class RecentSearchKeywordRepositoryImpl(
    private val recentSearchKeywordDataSource: RecentSearchKeywordDataSource
) : RecentSearchKeywordRepository {

    override suspend fun addRecentSearchKeyword(keyword: String) {
        recentSearchKeywordDataSource.addRecentSearchKeyword(keyword)
    }

    override fun getAllRecentSearchKeyword(): Flow<List<RecentSearchKeyword>> {
        return recentSearchKeywordDataSource.getAllRecentSearchKeyword().map {
            it.map { keyword ->
                keyword.toDomainModel()
            }
        }
    }

    override suspend fun removeAllRecentSearchKeyword() {
        recentSearchKeywordDataSource.removeAllRecentSearchKeyword()
    }

    override suspend fun removeRecentSearchKeyword(id: Long) {
        recentSearchKeywordDataSource.removeRecentSearchKeyword(id)
    }

}