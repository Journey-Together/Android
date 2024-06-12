package kr.tekit.lion.daongil.domain.usecase.place

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.domain.model.ListSearchOption
import kr.tekit.lion.daongil.domain.model.MapSearchOption
import kr.tekit.lion.daongil.domain.model.SearchPlace
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetSearchPlaceResultForMap(
    private val placeRepository: PlaceRepository
) {
    operator fun invoke(options: MapSearchOption): Flow<List<SearchPlace>>{
        return placeRepository.getSearchPlaceResultForMap(options)
    }
}