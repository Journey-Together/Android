package kr.tekit.lion.daongil.domain.usecase.areabased

import kr.tekit.lion.daongil.domain.model.BarrierFree

class GetAllBarrierFreeTouristSpotUseCase(
    private val fetchAllTouristSpotUseCase: FetchAllTouristSpotUseCase,
    private val fetchBarrierFreeUseCase: FetchBarrierFreeUseCase
) {
    suspend operator fun invoke(contentId: String): List<List<BarrierFree>> {
        return fetchAllTouristSpotUseCase(contentId).map{
            fetchBarrierFreeUseCase(it.contentId)
        }
    }
}