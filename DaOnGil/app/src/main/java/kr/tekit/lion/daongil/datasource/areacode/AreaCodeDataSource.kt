package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.local.VillageCodeEntity
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.model.VillageCode

interface AreaCodeDataSource {
    suspend fun getAreaInfoList(areaCode: String = ""): AreaCodeResponse

    suspend fun getAllAreaCodes(): List<AreaCodeEntity>

    suspend fun getAreaCodeInfo(code: String): AreaCodeEntity

    suspend fun addAreaCodeInfoList(areaCodes: List<AreaCode>)

    suspend fun addVillageCodeInfoList(villageCodes: List<VillageCode>)

    suspend fun getAllVillageInfoList(): List<VillageCodeEntity>

    suspend fun getAllVillageInfo(): VillageCodeEntity
}