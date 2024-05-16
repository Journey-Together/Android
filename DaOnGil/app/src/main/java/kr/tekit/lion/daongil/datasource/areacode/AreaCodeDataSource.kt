package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.local.AreaCodeEntity

interface AreaCodeDataSource {
    suspend fun getAreaCodes(request: Map<String, String>): AreaCodeResponse

    suspend fun getAreaCodes(): List<AreaCodeEntity>

    suspend fun getAreaCode(code: String): AreaCodeEntity

    suspend fun setAreaCode(areaCodes: List<AreaCodeEntity>)
}