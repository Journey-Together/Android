package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.ListSearchOption
import kr.tekit.lion.daongil.domain.model.ListSearchResult
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetSearchPlaceByList(private val placeRepository: PlaceRepository): BaseUseCase() {
    suspend operator fun invoke(options: ListSearchOption): Result<List<ListSearchResult>> = execute {
        placeRepository.getSearchPlaceResultByList(options)
    }
}