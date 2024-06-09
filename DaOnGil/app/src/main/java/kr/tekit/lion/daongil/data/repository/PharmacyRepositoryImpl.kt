package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PharmacyDataSource
import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo
import kr.tekit.lion.daongil.domain.repository.PharmacyRepository

class PharmacyRepositoryImpl(
    private val pharmacyDataSource: PharmacyDataSource
) : PharmacyRepository {
    override suspend fun getPharmacy(Q0: String?, Q1: String?): List<PharmacyMapInfo> {
        return pharmacyDataSource.getPharmacy(Q0, Q1).toDomainModel()
    }

}