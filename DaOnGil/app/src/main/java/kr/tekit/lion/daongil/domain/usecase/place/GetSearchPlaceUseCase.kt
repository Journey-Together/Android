package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.SearchOption
import kr.tekit.lion.daongil.domain.model.SearchPlace
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result


class GetSearchPlaceUseCase(
    private val placeRepository: PlaceRepository
): BaseUseCase() {
    suspend operator fun invoke(options: SearchOption): Result<List<SearchPlace>> = execute {
        placeRepository.getSearchPlaceResult(options)
    }
}