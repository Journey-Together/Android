package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class GetAllDetailAreaCodeUseCase(
    private val areaCodeRepository: VillageCodeRepository
) {
    suspend operator fun invoke(areaCode: String): List<VillageCode>{
        return areaCodeRepository.getAllVillageCode(areaCode)
    }
}