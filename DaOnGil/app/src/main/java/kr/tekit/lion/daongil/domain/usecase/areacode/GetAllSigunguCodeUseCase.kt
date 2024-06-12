package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.SigunguCode
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository

class GetAllSigunguCodeUseCase(
    private val areaCodeRepository: VillageCodeRepository
) {
    suspend operator fun invoke(areaCode: String): List<SigunguCode>{
        return areaCodeRepository.getAllSigunguCode(areaCode)
    }
}