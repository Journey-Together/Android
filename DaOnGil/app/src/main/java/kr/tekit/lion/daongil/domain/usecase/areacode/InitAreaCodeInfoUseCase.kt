package kr.tekit.lion.daongil.domain.usecase.areacode

import kr.tekit.lion.daongil.domain.model.VillageCode
import kr.tekit.lion.daongil.domain.repository.AreaCodeRepository
import kr.tekit.lion.daongil.domain.repository.VillageCodeRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class InitAreaCodeInfoUseCase(
    private val areaCodeRepository: AreaCodeRepository,
    private val villageCodeRepository: VillageCodeRepository,
): BaseUseCase() {
    suspend operator fun invoke(): Result<Unit> = execute {
        areaCodeRepository.addAreaCodeInfo()
        val areaCodes = areaCodeRepository.getAllAreaCodes()

        val sigunguCodeList = areaCodes.map { area->
            areaCodeRepository.getSigunguCode(area.code).map {
                VillageCode(area.code, it.code, it.name)
            }
        }

        sigunguCodeList.map { villageCodeRepository.addSigunguCode(it) }
    }
}