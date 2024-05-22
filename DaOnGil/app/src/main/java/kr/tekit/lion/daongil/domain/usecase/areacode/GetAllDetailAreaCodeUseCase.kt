package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository

class GetAllDetailAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository
) {
    suspend operator fun invoke(areaCode: String): List<AreaCode>{
        return areaCodeRepository.getDetailAreaCode(areaCode)
    }
}