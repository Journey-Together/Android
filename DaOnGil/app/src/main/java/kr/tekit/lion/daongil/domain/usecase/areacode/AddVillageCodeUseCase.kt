package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class AddVillageCodeUseCase(
    private val villageCodeRepository: VillageCodeRepository
) {
    suspend operator fun invoke(villageCode: List<VillageCode>) {
        villageCodeRepository.addVillageCode(villageCode)
    }
}