package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.local.SigunguCodeEntity
import kr.tekit.lion.daongil.data.local.dao.SigunguCodeDao

class SigunguCodeDataSource(
    private val sigunguCodeDao: SigunguCodeDao
) {
    suspend fun addSigunguCodeInfoList(villageCodes: List<SigunguCodeEntity>) {
        sigunguCodeDao.setVillageCode(villageCodes)
    }

    suspend fun getSigunguCodeByVillageName(villageName: String): String?{
        return sigunguCodeDao.getSigunguCodeByVillageName(villageName)
    }

    suspend fun getAllSigunguInfoList(code: String): List<SigunguCodeEntity> {
        return sigunguCodeDao.getSigunguCode(code)
    }
}