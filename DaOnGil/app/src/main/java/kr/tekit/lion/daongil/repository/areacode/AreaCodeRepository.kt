package kr.tekit.lion.daongil.repository.areacode

import kr.tekit.lion.daongil.datasource.areacode.LocalAreaCodeDataSource
import kr.tekit.lion.daongil.datasource.areacode.RemoteAreaCodeDataSource
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.network.RetrofitInstance
import kr.tekit.lion.daongil.network.service.KorWithService
import kr.tekit.lion.daongil.presentation.Constant.PUBLIC_DATA_PORTAL_BASE_URL

interface AreaCodeRepository {
    suspend fun getAreaCode(serviceKey: String, pageNo: String): AreaCodeResponse

    companion object{
        fun create(): AreaCodeRepository{
            return AreaCodeRepositoryImpl(
                RemoteAreaCodeDataSource(
                    RetrofitInstance.provideService(
                        PUBLIC_DATA_PORTAL_BASE_URL,
                        KorWithService::class.java
                    )
                ),
                LocalAreaCodeDataSource()
            )
        }
    }
}