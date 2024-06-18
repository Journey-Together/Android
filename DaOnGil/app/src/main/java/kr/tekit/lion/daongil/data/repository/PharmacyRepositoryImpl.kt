package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PharmacyDataSource
import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo
import kr.tekit.lion.daongil.domain.repository.PharmacyRepository

class PharmacyRepositoryImpl(
    private val pharmacyDataSource: PharmacyDataSource
) : PharmacyRepository {
    override suspend fun getPharmacy(q0: String?, q1: String?): List<PharmacyMapInfo> {
        return pharmacyDataSource.getPharmacy(q0, q1).toDomainModel()
    }
}