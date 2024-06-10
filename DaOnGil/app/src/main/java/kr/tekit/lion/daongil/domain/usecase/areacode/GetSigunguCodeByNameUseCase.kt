package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class GetSigunguCodeByNameUseCase(
    private val villageCodeRepository: VillageCodeRepository
) {
    suspend operator fun invoke(villageName: String): String?{
        return villageCodeRepository.getSigunguCodeByVillageName(villageName)
    }
}