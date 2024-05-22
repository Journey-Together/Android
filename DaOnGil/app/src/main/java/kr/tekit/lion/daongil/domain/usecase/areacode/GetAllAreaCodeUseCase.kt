package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository

class GetAllAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository,
) {
    suspend operator fun invoke(): List<AreaCode>{
        return areaCodeRepository.getAllAreaCodes()
    }
}