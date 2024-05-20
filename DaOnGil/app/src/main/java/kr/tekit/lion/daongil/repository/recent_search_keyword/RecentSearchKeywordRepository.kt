package kr.tekit.lion.daongil.repository.recent_search_keyword

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.datasource.RecentSearchKeywordDataSource
import kr.tekit.lion.daongil.local.MainDataBase
import kr.tekit.lion.daongil.model.RecentSearchKeyword

interface RecentSearchKeywordRepository {

    suspend fun addRecentSearchKeyword(keyword: String)

    suspend fun getAllRecentSearchKeyword(): Flow<List<RecentSearchKeyword>>

    suspend fun removeAllRecentSearchKeyword()

    suspend fun removeRecentSearchKeyword(id: Long)

    companion object{
        fun create(context: Context): RecentSearchKeywordRepositoryImpl{
            return RecentSearchKeywordRepositoryImpl(
                RecentSearchKeywordDataSource(
                    MainDataBase.getDatabase(context).RecentSearchKeywordDao()
                )
            )
        }
    }
}