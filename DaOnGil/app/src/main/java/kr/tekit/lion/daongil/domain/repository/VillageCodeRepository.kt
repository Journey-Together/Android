package kr.tekit.lion.daongil.domain.repository

import android.content.Context
import kr.tekit.lion.daongil.data.datasource.SigunguCodeDataSource
import kr.tekit.lion.daongil.data.local.MainDataBase
import kr.tekit.lion.daongil.domain.model.SigunguCode
import kr.tekit.lion.daongil.data.repository.SigunguCodeRepositoryImpl

interface VillageCodeRepository {

    suspend fun getSigunguCodeByVillageName(villageName: String): String?
    suspend fun getAllSigunguCode(code: String): List<SigunguCode>
    suspend fun addSigunguCode(sigunguCode: List<SigunguCode>)

    companion object{
        fun create(context: Context): SigunguCodeRepositoryImpl {
            return SigunguCodeRepositoryImpl(
                SigunguCodeDataSource(
                    MainDataBase.getDatabase(context).sigunguCodeDao()
                )
            )
        }
    }
}