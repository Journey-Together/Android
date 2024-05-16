package kr.tekit.lion.daongil.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface AreaCodeDao {
    @Query("SELECT * FROM area_code_table")
    suspend fun getAreaCodes(): List<AreaCodeEntity>

    @Query("SELECT * FROM area_code_table WHERE code = :areaCode")
    suspend fun getAreaCode(areaCode: String): AreaCodeEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAreaCode(areaCode: List<AreaCodeEntity>)

    @Update
    suspend fun updateMyPet(areaCode: AreaCodeEntity)

    @Query("DELETE FROM area_code_table WHERE code = :areaCode")
    suspend fun deleteMyPet(areaCode: String)
}

