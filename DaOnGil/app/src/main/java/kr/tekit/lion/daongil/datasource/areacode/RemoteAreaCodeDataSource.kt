package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.network.service.KorWithService

class RemoteAreaCodeDataSource(
    private val korWithService: KorWithService
) : AreaCodeDataSource {

    override suspend fun getAreaInfoList(): AreaCodeResponse {
        return korWithService.getAreaCode(AreaCodeRequest().toRequestModel())
    }

    override suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return emptyList()
    }


    override suspend fun getAreaCodeInfo(code: String): AreaCodeEntity {
        return AreaCodeEntity("", "")
    }

    override suspend fun addAreaInfoList(areaCodes: List<AreaCode>) { }

}