package kr.tekit.lion.daongil.data.datasource.areacode

import kr.tekit.lion.daongil.data.dto.remote.request.AreaCodeRequest
import kr.tekit.lion.daongil.data.dto.remote.response.areacode.AreaCodeResponse
import kr.tekit.lion.daongil.data.network.service.KorWithService

class RemoteAreaCodeDataSource(
    private val korWithService: KorWithService
) {
    suspend fun getAreaInfoList(code: String = ""): AreaCodeResponse {
        return korWithService.getAreaCode(AreaCodeRequest(code).toRequestModel())
    }
}