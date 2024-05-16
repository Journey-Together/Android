package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository

class SetAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository,
) {
    suspend operator fun invoke(areaCodes: List<AreaCode>){
        areaCodeRepository.setAreaCode(areaCodes)
    }
}