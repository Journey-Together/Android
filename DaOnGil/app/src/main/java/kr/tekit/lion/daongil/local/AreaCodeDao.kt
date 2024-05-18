package kr.tekit.lion.daongil.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AreaCodeDao {
    @Query("SELECT * FROM area_code_table")
    suspend fun getAreaCodes(): List<AreaCodeEntity>

    @Query("SELECT * FROM area_code_table WHERE code = :areaCode")
    suspend fun getAreaCode(areaCode: String): AreaCodeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAreaCode(areaCode: List<AreaCodeEntity>)
}

