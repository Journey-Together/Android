package kr.tekit.lion.daongil.usecase.areacode

import kr.tekit.lion.daongil.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.usecase.base.Result

class InitAreaCodeInfoUseCase(
    private val addAreaCodeUseCase: AddAreaCodeUseCase,
    private val getAllDetailAreaCodeUseCase: GetAllDetailAreaCodeUseCase,
    private val getAllAreaCodeUseCase: GetAllAreaCodeUseCase,
    private val addVillageCodeUseCase: AddVillageCodeUseCase
): BaseUseCase() {
    suspend operator fun invoke(): Result<Unit> = execute {
        addAreaCodeUseCase()

        val areaCodes = getAllAreaCodeUseCase()

        val detailCodes = areaCodes.map { getAllDetailAreaCodeUseCase(it.code) }

        detailCodes.mapIndexed { idx, detailCode ->
            addVillageCodeUseCase(areaCodes[idx].code, detailCode)
        }

    }
}