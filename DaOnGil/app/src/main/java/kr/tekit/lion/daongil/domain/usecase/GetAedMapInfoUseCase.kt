package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.AedMapInfo
import kr.tekit.lion.daongil.domain.repository.AedRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetAedMapInfoUseCase(
    private val aedRepository: AedRepository
) : BaseUseCase() {
    suspend operator fun invoke(Q0: String?, Q1: String?): Result<List<AedMapInfo>> =
        execute {
            aedRepository.getAedInfo(Q0, Q1)
        }
}