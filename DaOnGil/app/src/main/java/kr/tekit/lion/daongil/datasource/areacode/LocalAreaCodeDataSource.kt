package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse

class LocalAreaCodeDataSource: AreaCodeDataSource {
    override suspend fun getAreaCode(request: AreaCodeRequest): AreaCodeResponse {
        TODO("Not yet implemented")
    }

    override suspend fun setAreaCode() {
        TODO("Not yet implemented")
    }
}