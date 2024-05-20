package kr.tekit.lion.daongil.repository.recent_search_keyword

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.model.RecentSearchKeyword

interface RecentSearchKeywordRepository {

    suspend fun addRecentSearchKeyword(keyword: RecentSearchKeyword)

    suspend fun getAllRecentSearchKeyword(): Flow<List<RecentSearchKeyword>>

    suspend fun removeAllRecentSearchKeyword()

    suspend fun removeRecentSearchKeyword(id: Long)
}