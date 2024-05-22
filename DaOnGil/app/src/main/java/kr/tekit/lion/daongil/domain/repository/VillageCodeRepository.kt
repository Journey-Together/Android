package kr.tekit.lion.daongil.domain.repository

import android.content.Context
import kr.tekit.lion.daongil.data.datasource.LocalVillageCodeDataSource
import kr.tekit.lion.daongil.data.local.MainDataBase
import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.data.repository.VillageCodeRepositoryImpl

interface VillageCodeRepository {
    suspend fun getAllVillageCode(): List<VillageCode>
    suspend fun addVillageCode(areaCode: String, villageCode: List<AreaCode>)

    companion object{
        fun create(context: Context): VillageCodeRepositoryImpl {
            return VillageCodeRepositoryImpl(
                LocalVillageCodeDataSource(
                    MainDataBase.getDatabase(context).villageCodeDao()
                )
            )
        }
    }
}