package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.emergency.pharmacy.PharmacyResponse
import kr.tekit.lion.daongil.data.network.service.PharmacyService

class PharmacyDataSource(
    private val pharmacyService: PharmacyService
) {
    suspend fun getPharmacy(Q0: String?, Q1: String?): PharmacyResponse {
        return pharmacyService.getPharmacy(Q0, Q1)
    }
}