package kr.tekit.lion.daongil.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.tekit.lion.daongil.data.dto.local.VillageCodeEntity

@Dao
interface VillageCodeDao {

    @Query("SELECT * FROM VILLAGE_CODE_TABLE")
    suspend fun getAllVillageCode(): List<VillageCodeEntity>

    @Query("SELECT * FROM VILLAGE_CODE_TABLE WHERE villageCode = :code")
    suspend fun getVillageCode(code: String): VillageCodeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setVillageCode(villageCodes: List<VillageCodeEntity>)

}