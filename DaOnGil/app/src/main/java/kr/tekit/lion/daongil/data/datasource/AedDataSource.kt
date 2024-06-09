package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.emergency.aed.AedResponse
import kr.tekit.lion.daongil.data.network.service.AedService

class AedDataSource(
    private val aedService: AedService
) {
    suspend fun getAedInfo(Q0: String?, Q1: String?) : AedResponse {
        return aedService.getAedInfo(Q0, Q1)
    }
}