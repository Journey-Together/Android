package kr.tekit.lion.daongil.domain.usecase.emergency

import kr.tekit.lion.daongil.domain.model.PharmacyMapInfo
import kr.tekit.lion.daongil.domain.repository.PharmacyRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPharmacyMapInfoUseCase(
    private val pharmacyRepository: PharmacyRepository
) : BaseUseCase() {

    suspend operator fun invoke(q0: String?, q1: String?): Result<List<PharmacyMapInfo>> = execute {
        pharmacyRepository.getPharmacy(q0, q1)
    }
}