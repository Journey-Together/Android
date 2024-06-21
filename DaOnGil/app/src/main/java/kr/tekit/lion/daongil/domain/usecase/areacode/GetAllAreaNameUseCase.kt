package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository

class GetAllAreaNameUseCase(
    private val areaCodeRepository: AreaCodeRepository,
) {
    suspend operator fun invoke(): List<String>{
        return areaCodeRepository.getAllAreaCodes().map { it.name }
    }
}