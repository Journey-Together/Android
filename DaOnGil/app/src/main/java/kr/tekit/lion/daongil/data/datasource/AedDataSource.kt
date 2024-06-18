package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.emergency.aed.AedResponse
import kr.tekit.lion.daongil.data.network.service.AedService

class AedDataSource(
    private val aedService: AedService
) {
    suspend fun getAedInfo(q0: String?, q1: String?) : AedResponse {
        return aedService.getAedInfo(q0, q1)
    }
}