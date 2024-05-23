package kr.tekit.lion.daongil.domain.usecase.areabased

import kr.tekit.lion.daongil.domain.model.BarrierFree

class GetAllBarrierFreeTouristSpotUseCase(
    private val fetchAllTouristSpotUseCase: FetchAllTouristSpotUseCase,
    private val fetchBarrierFreeUseCase: FetchBarrierFreeUseCase
) {
    suspend operator fun invoke(): List<List<BarrierFree>> {
        return fetchAllTouristSpotUseCase().map{
            fetchBarrierFreeUseCase(it.contentId)
        }
    }
}