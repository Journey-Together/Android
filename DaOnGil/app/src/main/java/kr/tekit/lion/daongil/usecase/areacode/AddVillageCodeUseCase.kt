package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.repository.villagecode.VillageCodeRepository

class AddVillageCodeUseCase(
    private val villageCodeRepository: VillageCodeRepository
) {
    suspend operator fun invoke(areaCode: String, villageCode: List<AreaCode>){
        villageCodeRepository.addVillageCode(areaCode, villageCode)
    }
}