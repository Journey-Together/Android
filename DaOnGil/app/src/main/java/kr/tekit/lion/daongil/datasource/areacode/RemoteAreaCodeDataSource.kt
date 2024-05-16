package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.network.service.KorWithService

class RemoteAreaCodeDataSource(
    private val korWithService: KorWithService
): AreaCodeDataSource {

    override suspend fun getAreaCodes(request: Map<String, String>): AreaCodeResponse {
        return korWithService.getAreaCode(request)
    }

    override suspend fun getAreaCodes(): List<AreaCodeEntity> {
        return emptyList()
    }

    override suspend fun getAreaCode(code: String): AreaCodeEntity {
        return AreaCodeEntity("","")
    }

    override suspend fun setAreaCode(areaCodes: List<AreaCodeEntity>){}

}