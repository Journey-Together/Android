package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeDao
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.local.toEntity
import kr.tekit.lion.daongil.model.AreaCode

class LocalAreaCodeDataSource(
    private val areaCodeDao: AreaCodeDao
): AreaCodeDataSource {

    override suspend fun getAreaInfoList(): AreaCodeResponse {
        TODO()
    }

    override suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return areaCodeDao.getAreaCodes()
    }

    override suspend fun getAreaCodeInfo(code: String): AreaCodeEntity {
        return areaCodeDao.getAreaCode(code)
    }

    override suspend fun addAreaInfoList(areaCodes: List<AreaCode>) {
        val entities = areaCodes.map { it.toEntity() }
        areaCodeDao.insertAreaCode(entities)
    }
}