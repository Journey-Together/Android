package kr.tekit.lion.daongil.repository.recent_search_keyword

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.tekit.lion.daongil.datasource.RecentSearchKeywordDataSource
import kr.tekit.lion.daongil.local.entity.toDomainModel
import kr.tekit.lion.daongil.local.entity.toEntity
import kr.tekit.lion.daongil.model.RecentSearchKeyword

class RecentSearchKeywordRepositoryImpl(
    private val recentSearchKeywordDataSource: RecentSearchKeywordDataSource
) : RecentSearchKeywordRepository {

    override suspend fun addRecentSearchKeyword(keyword: String) {
        recentSearchKeywordDataSource.addRecentSearchKeyword(
            RecentSearchKeyword(keyword = keyword).toEntity()
        )
    }

    override suspend fun getAllRecentSearchKeyword(): Flow<List<RecentSearchKeyword>> {
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