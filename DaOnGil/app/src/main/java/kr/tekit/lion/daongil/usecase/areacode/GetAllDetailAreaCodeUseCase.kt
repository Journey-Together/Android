package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository

class GetAllDetailAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository
) {
    suspend operator fun invoke(areaCode: String): List<AreaCode>{
        return areaCodeRepository.getDetailAreaCode(areaCode)
    }
}