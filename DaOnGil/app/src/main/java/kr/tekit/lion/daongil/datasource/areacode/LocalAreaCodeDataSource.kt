package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.dao.AreaCodeDao
import kr.tekit.lion.daongil.local.entity.AreaCodeEntity
import kr.tekit.lion.daongil.local.dao.VillageCodeDao
import kr.tekit.lion.daongil.local.entity.VillageCodeEntity
import kr.tekit.lion.daongil.local.entity.toEntity
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.model.VillageCode

class LocalAreaCodeDataSource(
    private val areaCodeDao: AreaCodeDao,
    private val villageCodeDao: VillageCodeDao
): AreaCodeDataSource {

    override suspend fun getAllAreaCodes(): List<AreaCodeEntity> {
        return areaCodeDao.getAreaCodes()
    }

    override suspend fun getAreaCodeInfo(code: String): AreaCodeEntity {
        return areaCodeDao.getAreaCode(code)
    }

    override suspend fun addAreaCodeInfoList(areaCodes: List<AreaCode>) {
        val entities = areaCodes.map { it.toEntity() }
        areaCodeDao.insertAreaCode(entities)
    }

    override suspend fun addVillageCodeInfoList(villageCodes: List<VillageCode>) {
        villageCodeDao.setVillageCode(villageCodes.map { it.toEntity() })
    }

    override suspend fun getAllVillageInfoList(): List<VillageCodeEntity> {
        return villageCodeDao.getAllVillageCode()
    }

    override suspend fun getAreaInfoList(areaCode: String): AreaCodeResponse { TODO() }

    override suspend fun getAllVillageInfo(): VillageCodeEntity { TODO() }
}