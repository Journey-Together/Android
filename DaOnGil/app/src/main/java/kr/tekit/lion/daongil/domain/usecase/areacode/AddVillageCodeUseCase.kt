package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class AddVillageCodeUseCase(
    private val villageCodeRepository: VillageCodeRepository
) {
    suspend operator fun invoke(areaCode: String, villageCode: List<AreaCode>){
        villageCodeRepository.addVillageCode(areaCode, villageCode)
    }
}