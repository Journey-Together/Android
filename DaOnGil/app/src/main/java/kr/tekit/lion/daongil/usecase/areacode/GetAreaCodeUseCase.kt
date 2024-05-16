package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.local.toDomainModel
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository

class GetAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository,
) {
    suspend operator fun invoke(code: String): AreaCode{
        return areaCodeRepository.getAreaCode(code).toDomainModel()
    }
}