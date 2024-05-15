package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse

interface AreaCodeDataSource {
    suspend fun getAreaCode(request: AreaCodeRequest): AreaCodeResponse
    suspend fun setAreaCode()
}