package kr.tekit.lion.daongil.domain.usecase.areabased

import kr.tekit.lion.daongil.domain.model.AreaBased
import kr.tekit.lion.daongil.domain.repository.AreaBasedRepository

class FetchAllTouristSpotUseCase (
    private val areaBasedRepository: AreaBasedRepository
){
    suspend operator fun invoke(contentId: String): List<AreaBased> {
        return areaBasedRepository.searchAllTouristSpot(contentId)
    }
}