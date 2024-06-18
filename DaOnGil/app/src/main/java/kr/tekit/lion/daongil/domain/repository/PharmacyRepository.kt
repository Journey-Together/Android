package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PharmacyDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.PharmacyRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo

interface PharmacyRepository {

    suspend fun getPharmacy(q0: String?, q1: String?): List<PharmacyMapInfo>

    companion object {
        fun create(): PharmacyRepositoryImpl{
            return PharmacyRepositoryImpl(
                PharmacyDataSource(
                    RetrofitInstance.pharmacyService
                )
            )
        }
    }
}