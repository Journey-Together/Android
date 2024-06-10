package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo
import kr.tekit.lion.daongil.domain.repository.PharmacyRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPharmacyMapInfoUseCase(
    private val pharmacyRepository: PharmacyRepository
) : BaseUseCase() {
    suspend operator fun invoke(Q0: String?, Q1: String?): Result<List<PharmacyMapInfo>> =
        execute {
            pharmacyRepository.getPharmacy(Q0, Q1)
        }
}