package kr.tekit.lion.daongil.data.datasource

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.data.local.dao.RecentSearchKeywordDao
import kr.tekit.lion.daongil.data.dto.local.RecentSearchKeywordEntity

class RecentSearchKeywordDataSource(
    private val recentSearchKeywordDao: RecentSearchKeywordDao
) {
    fun getAllRecentSearchKeyword(): Flow<List<RecentSearchKeywordEntity>> {
        return recentSearchKeywordDao.readAllKeyword()
    }

    suspend fun addRecentSearchKeyword(keywordEntity: RecentSearchKeywordEntity){
        recentSearchKeywordDao.insertKeyword(keywordEntity)
    }

    suspend fun removeAllRecentSearchKeyword(){
        recentSearchKeywordDao.deleteAllKeyword()
    }

    suspend fun removeRecentSearchKeyword(id: Long){
        recentSearchKeywordDao.deleteKeyword(id)
    }
}