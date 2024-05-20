package kr.tekit.lion.daongil.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.local.entity.RecentSearchKeywordEntity

@Dao
interface RecentSearchKeywordDao {

    @Query("SELECT * FROM search_keyword_table")
    fun readAllKeyword(): Flow<List<RecentSearchKeywordEntity>>

    @Insert
    suspend fun insertKeyword(keyword: RecentSearchKeywordEntity)

    @Query("DELETE FROM search_keyword_table where id = :id ")
    suspend fun deleteKeyword(id: Long)

    @Query("DELETE FROM search_keyword_table")
    suspend fun deleteAllKeyword()
}