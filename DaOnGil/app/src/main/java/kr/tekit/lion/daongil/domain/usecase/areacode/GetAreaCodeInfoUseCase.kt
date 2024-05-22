package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.AreaCode
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository

class GetAreaCodeInfoUseCase(
    private val areaCodeRepository: AreaCodeRepository
) {
    suspend operator fun invoke(code: String): AreaCode {
        return areaCodeRepository.getAreaCodeInfo(code)
    }
}