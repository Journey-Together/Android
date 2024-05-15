package kr.tekit.lion.daongil.usecase

import kr.tekit.lion.daongil.dto.request.AreaCodeRequest
import kr.tekit.lion.daongil.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.model.AreaCode
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository
import kr.tekit.lion.daongil.usecase.base.Result

class GetAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository
): BaseUseCase() {
    suspend operator fun invoke(request: AreaCodeRequest): Result<List<AreaCode>> = execute {
        areaCodeRepository.getAreaCode(request).toDomainModel()
    }
}