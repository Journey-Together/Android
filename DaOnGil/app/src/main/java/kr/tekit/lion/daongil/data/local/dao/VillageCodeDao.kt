package kr.tekit.lion.daongil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.tekit.lion.daongil.data.dto.local.VillageCodeEntity

@Dao
interface VillageCodeDao {

    @Query("SELECT villageCode FROM VILLAGE_CODE_TABLE WHERE villageName LIKE :villageName || '%' ")
    suspend fun getVillageCodeByVillageName(villageName: String): String?

    @Query("SELECT * FROM VILLAGE_CODE_TABLE WHERE areaCode = :code")
    suspend fun getVillageCode(code: String): List<VillageCodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setVillageCode(villageCodes: List<VillageCodeEntity>)

}