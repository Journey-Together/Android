package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository

class GetAllAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository,
) {
    suspend operator fun invoke(): List<AreaCode>{
        return areaCodeRepository.getAllAreaCodes()
    }
}