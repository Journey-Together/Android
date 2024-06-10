package kr.tekit.lion.daongil.domain.repository

import android.content.Context
import kr.tekit.lion.daongil.data.datasource.LocalSigunguCodeDataSource
import kr.tekit.lion.daongil.data.local.MainDataBase
import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.data.repository.SigunguCodeRepositoryImpl

interface VillageCodeRepository {

    suspend fun getSigunguCodeByVillageName(villageName: String): String?
    suspend fun getAllSigunguCode(code: String): List<VillageCode>
    suspend fun addSigunguCode(villageCode: List<VillageCode>)

    companion object{
        fun create(context: Context): SigunguCodeRepositoryImpl {
            return SigunguCodeRepositoryImpl(
                LocalSigunguCodeDataSource(
                    MainDataBase.getDatabase(context).villageCodeDao()
                )
            )
        }
    }
}