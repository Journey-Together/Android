package kr.tekit.lion.daongil.data.datasource

import kr.tekit.lion.daongil.data.dto.remote.response.emergency.pharmacy.PharmacyResponse
import kr.tekit.lion.daongil.data.network.service.PharmacyService

class PharmacyDataSource(
    private val pharmacyService: PharmacyService
) {
    suspend fun getPharmacy(q0: String?, q1: String?): PharmacyResponse {
        return pharmacyService.getPharmacy(q0, q1)
    }
}