package kr.tekit.lion.daongil.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.data.datasource.RecentSearchKeywordDataSource
import kr.tekit.lion.daongil.data.local.MainDataBase
import kr.tekit.lion.daongil.domain.model.RecentSearchKeyword
import kr.tekit.lion.daongil.data.repository.RecentSearchKeywordRepositoryImpl

interface RecentSearchKeywordRepository {

    suspend fun addRecentSearchKeyword(keyword: String)

    fun getAllRecentSearchKeyword(): Flow<List<RecentSearchKeyword>>

    suspend fun removeAllRecentSearchKeyword()

    suspend fun removeRecentSearchKeyword(id: Long)

    companion object{
        fun create(context: Context): RecentSearchKeywordRepositoryImpl {
            return RecentSearchKeywordRepositoryImpl(
                RecentSearchKeywordDataSource(
                    MainDataBase.getDatabase(context).RecentSearchKeywordDao()
                )
            )
        }
    }
}