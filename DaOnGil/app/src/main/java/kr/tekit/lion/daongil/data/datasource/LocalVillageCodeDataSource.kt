package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.local.dao.VillageCodeDao
import kr.tekit.lion.daongil.data.dto.local.VillageCodeEntity

class LocalVillageCodeDataSource(
    private val villageCodeDao: VillageCodeDao
) {
    suspend fun addVillageCodeInfoList(villageCodes: List<VillageCodeEntity>) {
        villageCodeDao.setVillageCode(villageCodes)
    }

    suspend fun getAllVillageInfoList(code: String): List<VillageCodeEntity> {
        return villageCodeDao.getVillageCode(code)
    }
}