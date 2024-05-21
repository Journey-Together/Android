package kr.tekit.lion.daongil.repository.villagecode

import android.content.Context
import kr.tekit.lion.daongil.datasource.LocalVillageCodeDataSource
import kr.tekit.lion.daongil.local.MainDataBase
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.model.VillageCode

interface VillageCodeRepository {
    suspend fun getAllVillageCode(): List<VillageCode>
    suspend fun addVillageCode(areaCode: String, villageCode: List<AreaCode>)

    companion object{
        fun create(context: Context): VillageCodeRepositoryImpl{
            return VillageCodeRepositoryImpl(
                LocalVillageCodeDataSource(
                    MainDataBase.getDatabase(context).villageCodeDao()
                )
            )
        }
    }
}