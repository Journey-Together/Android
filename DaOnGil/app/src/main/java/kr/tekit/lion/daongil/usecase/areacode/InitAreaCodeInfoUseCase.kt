package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.repository.areacode.AreaCodeRepository
import kr.tekit.lion.daongil.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.usecase.base.Result

class InitAreaCodeInfoUseCase(
    private val areaCodeRepository: AreaCodeRepository,
): BaseUseCase() {
    suspend operator fun invoke(): Result<Unit> = execute {
        areaCodeRepository.addAreaCodeInfo()
        areaCodeRepository.addVillageCodeInfo()
    }
}