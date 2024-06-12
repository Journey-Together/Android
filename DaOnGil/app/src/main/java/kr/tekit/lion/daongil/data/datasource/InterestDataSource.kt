package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.Interest.InterestTypeRes
import kr.tekit.lion.daongil.data.network.service.InterestService

class InterestDataSource(
    private val interestService: InterestService
) {
    suspend fun getInterestType(): InterestTypeRes {
        return interestService.getInterestType()
    }

    suspend fun updateInterestType(interestType: InterestTypeRes) {
        return interestService.updateInterestType(interestType)
    }
}