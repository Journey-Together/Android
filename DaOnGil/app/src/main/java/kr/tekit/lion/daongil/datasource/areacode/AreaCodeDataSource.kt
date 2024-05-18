package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeEntity
import kr.tekit.lion.daongil.model.AreaCode

interface AreaCodeDataSource {
    suspend fun getAreaInfoList(): AreaCodeResponse

    suspend fun getAllAreaCodes(): List<AreaCodeEntity>

    suspend fun getAreaCodeInfo(code: String): AreaCodeEntity

    suspend fun addAreaInfoList(areaCodes: List<AreaCodeEntity>)
}