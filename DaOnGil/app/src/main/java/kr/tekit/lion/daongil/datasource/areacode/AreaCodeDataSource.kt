package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse

interface AreaCodeDataSource {
    suspend fun getAreaCode(request: Map<String, String>): AreaCodeResponse
    suspend fun setAreaCode()
}