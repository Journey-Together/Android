package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository
import kr.tekit.lion.daongil.usecase.base.Result

class InitAreaCodeUseCase(
    private val areaCodeRepository: AreaCodeRepository,
    private val setAreaCodeUseCase: SetAreaCodeUseCase
): BaseUseCase() {
    suspend operator fun invoke(serviceKey: String): Result<Unit> = execute {

        setAreaCodeUseCase(areaCodeRepository.getAreaCode(serviceKey, "1").toDomainModel())
        setAreaCodeUseCase(areaCodeRepository.getAreaCode(serviceKey, "2").toDomainModel())

    }
}