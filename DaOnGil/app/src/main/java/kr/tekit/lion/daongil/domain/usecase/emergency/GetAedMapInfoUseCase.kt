package kr.tekit.lion.daongil.domain.usecase.emergency

import kr.tekit.lion.daongil.domain.model.AedMapInfo
import kr.tekit.lion.daongil.domain.repository.AedRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetAedMapInfoUseCase(
    private val aedRepository: AedRepository
) : BaseUseCase() {
    suspend operator fun invoke(q0: String?, q1: String?): Result<List<AedMapInfo>> = execute {
        aedRepository.getAedInfo(q0, q1)
    }
}