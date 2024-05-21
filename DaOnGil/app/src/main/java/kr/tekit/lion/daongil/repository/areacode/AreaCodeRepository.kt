package kr.tekit.lion.daongil.repository.areacode

import android.content.Context
import kr.tekit.lion.daongil.datasource.areacode.LocalAreaCodeDataSource
import kr.tekit.lion.daongil.datasource.areacode.RemoteAreaCodeDataSource
import kr.tekit.lion.daongil.local.MainDataBase
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.network.RetrofitInstance

interface AreaCodeRepository {

    suspend fun getAreaCodeInfo(): List<AreaCode>
    suspend fun getAreaCodeInfo(code: String): AreaCode
    suspend fun getAllAreaCodes(): List<AreaCode>
    suspend fun getDetailAreaCode(areaCode: String): List<AreaCode>
    suspend fun addAreaCodeInfo()

    companion object{
        fun create(context: Context): AreaCodeRepository{
            return AreaCodeRepositoryImpl(
                RemoteAreaCodeDataSource(RetrofitInstance.korWithService),
                LocalAreaCodeDataSource(MainDataBase.getDatabase(context).areaCodeDao())
            )
        }
    }
}