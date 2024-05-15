package kr.tekit.lion.daongil.usecase

import kr.tekit.lion.daongil.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository
import kr.tekit.lion.daongil.usecase.base.Result

class GetAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository
): BaseUseCase() {
    suspend operator fun invoke(request: Map<String, String>): Result<List<AreaCode>> = execute {
        areaCodeRepository.getAreaCode(request).toDomainModel()
    }
}