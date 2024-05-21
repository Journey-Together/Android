package kr.tekit.lion.daongil.datasource

import kr.tekit.lion.daongil.local.dao.VillageCodeDao
import kr.tekit.lion.daongil.local.entity.VillageCodeEntity

class LocalVillageCodeDataSource(
    private val villageCodeDao: VillageCodeDao
) {
    suspend fun addVillageCodeInfoList(villageCodes: List<VillageCodeEntity>) {
        villageCodeDao.setVillageCode(villageCodes)
    }

    suspend fun getAllVillageInfoList(): List<VillageCodeEntity> {
        return villageCodeDao.getAllVillageCode()
    }
}