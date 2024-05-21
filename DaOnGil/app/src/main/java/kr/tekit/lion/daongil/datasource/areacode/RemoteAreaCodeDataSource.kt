package kr.tekit.lion.daongil.datasource.areacode

import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.dto.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.network.service.KorWithService

class RemoteAreaCodeDataSource(
    private val korWithService: KorWithService
) {
    suspend fun getAreaInfoList(code: String = ""): AreaCodeResponse {
        return korWithService.getAreaCode(AreaCodeRequest(code).toRequestModel())
    }
}