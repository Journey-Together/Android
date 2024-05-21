package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.local.dao.AreaCodeDao
import kr.tekit.lion.daongil.local.entity.AreaCodeEntity

class LocalAreaCodeDataSource(
    private val areaCodeDao: AreaCodeDao,
) {
    suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return areaCodeDao.getAreaCodes()
    }

    suspend fun getAreaCodeInfo(code: String): AreaCodeEntity {
        return areaCodeDao.getAreaCode(code)
    }

    suspend fun addAreaCodeInfoList(areaCodeEntity: List<AreaCodeEntity>) {
        areaCodeDao.insertAreaCode(areaCodeEntity)
    }
}