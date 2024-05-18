package kr.tekit.lion.daongil.repository.areacode

import android.content.Context
import kr.tekit.lion.daongil.datasource.areacode.LocalAreaCodeDataSource
import kr.tekit.lion.daongil.datasource.areacode.RemoteAreaCodeDataSource
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.local.MainDataBase
import kr.tekit.lion.daongil.network.RetrofitInstance

interface AreaCodeRepository {

    suspend fun getAreaCodeInfo(code: String): AreaCodeEntity
    suspend fun getAllAreaCodes(): List<AreaCodeEntity>
    suspend fun fetchAreaCodeInfo()
    suspend fun fetchVillageCodeInfo()

    companion object{
        fun create(context: Context): AreaCodeRepository{
            return AreaCodeRepositoryImpl(
                RemoteAreaCodeDataSource(RetrofitInstance.korWithService),
                LocalAreaCodeDataSource(
                    MainDataBase.getDatabase(context).areaCodeDao(),
                    MainDataBase.getDatabase(context).villageCodeDao()
                )
            )
        }
    }
}