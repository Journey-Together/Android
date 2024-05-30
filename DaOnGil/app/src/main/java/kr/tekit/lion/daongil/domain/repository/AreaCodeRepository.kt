package kr.tekit.lion.daongil.domain.repository

import android.content.Context
import kr.tekit.lion.daongil.data.datasource.areacode.LocalAreaCodeDataSource
import kr.tekit.lion.daongil.data.datasource.areacode.RemoteAreaCodeDataSource
import kr.tekit.lion.daongil.data.local.MainDataBase
import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.AreaCodeRepositoryImpl

interface AreaCodeRepository {

    suspend fun getAreaCodeInfo(): List<AreaCode>
    suspend fun getAreaCodeInfo(code: String): AreaCode
    suspend fun getAllAreaCodes(): List<AreaCode>
    suspend fun addAreaCodeInfo()

    companion object{
        fun create(context: Context): AreaCodeRepository {
            return AreaCodeRepositoryImpl(
                RemoteAreaCodeDataSource(RetrofitInstance.korWithService),
                LocalAreaCodeDataSource(MainDataBase.getDatabase(context).areaCodeDao())
            )
        }
    }
}