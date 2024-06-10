package kr.tekit.lion.daongil.data.datasource.areacode

import kr.tekit.lion.daongil.data.local.dao.AreaCodeDao
import kr.tekit.lion.daongil.data.dto.local.AreaCodeEntity

class LocalAreaCodeDataSource(
    private val areaCodeDao: AreaCodeDao,
) {
    suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return areaCodeDao.getAreaCodes()
    }

    suspend fun getAreaCodeInfo(code: String): String? {
        return areaCodeDao.getAreaCode(code)
    }

    suspend fun addAreaCodeInfoList(areaCodeEntity: List<AreaCodeEntity>) {
        areaCodeDao.insertAreaCode(areaCodeEntity)
    }
}