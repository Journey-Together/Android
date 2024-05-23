package kr.tekit.lion.daongil.domain.usecase.areabased

import kr.tekit.lion.daongil.domain.model.AreaBased
import kr.tekit.lion.daongil.domain.repository.AreaBasedRepository

class SearchAllTouristSpotUseCase (
    private val areaBasedRepository: AreaBasedRepository
){
    suspend operator fun invoke(): List<AreaBased> {
        return areaBasedRepository.searchAllTouristSpot()
    }
}