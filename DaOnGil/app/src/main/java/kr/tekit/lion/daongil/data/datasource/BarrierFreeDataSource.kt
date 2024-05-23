package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.request.BarrierFreeRequest
import kr.tekit.lion.daongil.data.dto.remote.response.barrierfree.BarrierFreeResponse
import kr.tekit.lion.daongil.data.network.service.KorWithService

class BarrierFreeDataSource(
    private val korWithService: KorWithService
) {
    suspend fun getBarrierFreeInfo(contentId: String): BarrierFreeResponse {
        return korWithService.getBarrierFreeInfo(BarrierFreeRequest(contentId).toRequestModel())
    }
}