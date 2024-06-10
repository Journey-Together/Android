package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.local.dao.VillageCodeDao
import kr.tekit.lion.daongil.data.dto.local.VillageCodeEntity

class LocalSigunguCodeDataSource(
    private val villageCodeDao: VillageCodeDao
) {
    suspend fun addSigunguCodeInfoList(villageCodes: List<VillageCodeEntity>) {
        villageCodeDao.setVillageCode(villageCodes)
    }

    suspend fun getSigunguCodeByVillageName(villageName: String): String?{
        return villageCodeDao.getVillageCodeByVillageName(villageName)
    }

    suspend fun getAllSigunguInfoList(code: String): List<VillageCodeEntity> {
        return villageCodeDao.getVillageCode(code)
    }
}