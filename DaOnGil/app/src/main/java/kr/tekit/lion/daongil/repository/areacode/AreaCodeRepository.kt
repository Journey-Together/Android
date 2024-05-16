package kr.tekit.lion.daongil.repository.areacode

import android.content.Context
import kr.tekit.lion.daongil.datasource.areacode.LocalAreaCodeDataSource
import kr.tekit.lion.daongil.datasource.areacode.RemoteAreaCodeDataSource
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.local.MainDataBase
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.network.RetrofitInstance
import kr.tekit.lion.daongil.network.service.KorWithService
import kr.tekit.lion.daongil.presentation.Constant.PUBLIC_DATA_PORTAL_BASE_URL

interface AreaCodeRepository {

    suspend fun getAreaCode(serviceKey: String, pageNo: String): AreaCodeResponse
    suspend fun getAreaCode(code: String): AreaCodeEntity
    suspend fun getAreaCodes(): List<AreaCodeEntity>
    suspend fun setAreaCode(areaCode : List<AreaCode>)

    companion object{
        fun create(context: Context): AreaCodeRepository{
            return AreaCodeRepositoryImpl(
                RemoteAreaCodeDataSource(
                    RetrofitInstance.provideService(
                        PUBLIC_DATA_PORTAL_BASE_URL,
                        KorWithService::class.java
                    ),
                ),
                LocalAreaCodeDataSource(MainDataBase.getDatabase(context).areaCodeDao())
            )
        }
    }
}