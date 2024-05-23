package kr.tekit.lion.daongil.domain.usecase.areabased

import kr.tekit.lion.daongil.domain.model.BarrierFree
import kr.tekit.lion.daongil.domain.repository.BarrierFreeRepository

class FetchBarrierFreeUseCase(
    private val barrierFreeRepository: BarrierFreeRepository
){
    suspend operator fun invoke(contentId: String): List<BarrierFree> {
        return barrierFreeRepository.getBarrierFreeInfo(contentId)
    }
}
