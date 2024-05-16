package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeDao
import kr.tekit.lion.daongil.local.AreaCodeEntity

class LocalAreaCodeDataSource(
    private val areaCodeDao: AreaCodeDao
): AreaCodeDataSource {

    override suspend fun getAreaCodes(request: Map<String, String>): AreaCodeResponse {
        TODO()
    }

    override suspend fun getAreaCodes(): List<AreaCodeEntity> {
        return areaCodeDao.getAreaCodes()
    }

    override suspend fun getAreaCode(code: String): AreaCodeEntity {
        return areaCodeDao.getAreaCode(code)
    }

    override suspend fun setAreaCode(areaCodes: List<AreaCodeEntity>){
        areaCodeDao.insertAreaCode(areaCodes)
    }
}