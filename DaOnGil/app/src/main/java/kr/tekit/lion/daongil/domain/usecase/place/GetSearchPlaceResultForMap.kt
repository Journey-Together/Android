package kr.tekit.lion.daongil.domain.usecase.place

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.domain.model.MapSearchOption
import kr.tekit.lion.daongil.domain.model.MapSearchResult
import kr.tekit.lion.daongil.domain.repository.PlaceRepository

class GetSearchPlaceResultForMap(
    private val placeRepository: PlaceRepository
) {
    operator fun invoke(options: MapSearchOption): Flow<List<MapSearchResult>> {
        return placeRepository.getSearchPlaceResultForMap(options)
    }
}