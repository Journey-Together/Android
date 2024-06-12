package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository

class GetAreaCodeByNameUseCase(
    private val areaCodeRepository: AreaCodeRepository
) {
    suspend operator fun invoke(areaName: String): String? {
        return areaCodeRepository.getAreaCodeByName(areaName)
    }
}