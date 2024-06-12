package kr.tekit.lion.daongil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.tekit.lion.daongil.data.dto.local.SigunguCodeEntity

@Dao
interface SigunguCodeDao {

    @Query("SELECT sigunguCode FROM VILLAGE_CODE_TABLE WHERE sigunguName LIKE :villageName || '%' ")
    suspend fun getSigunguCodeByVillageName(villageName: String): String?

    @Query("SELECT * FROM VILLAGE_CODE_TABLE WHERE areaCode = :code")
    suspend fun getSigunguCode(code: String): List<SigunguCodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setVillageCode(villageCodes: List<SigunguCodeEntity>)

}