package kr.tekit.lion.daongil.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.tekit.lion.daongil.data.dto.remote.response.detailplace.DetailPlaceResponse
import kr.tekit.lion.daongil.data.dto.remote.response.searchplace.SearchPlaceResponse
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.domain.model.ListSearchOption
import kr.tekit.lion.daongil.domain.model.MapSearchOption

class PlaceDataSource(
    private val placeService: PlaceService
) {
    suspend fun getPlaceDetailInfo(placeId: Long): DetailPlaceResponse {
        return placeService.getPlaceDetailInfo(placeId)
    }

    suspend fun getSearchPlaceResultForList(request: ListSearchOption): SearchPlaceResponse {
        return placeService.searchPlace(
            category = request.category,
            query = request.query,
            size = request.size,
            page = request.page,
            minX = request.minX,
            maxX = request.maxX,
            minY = request.minY,
            maxY = request.maxY,
            disabilityType = request.disabilityType.toList(),
            detailFilter = request.detailFilter.toList(),
            areaCode = request.areaCode,
            sigunguCode = request.sigunguCode,
            arrange = request.arrange
        )
    }

     fun getSearchPlaceResultForMap(request: MapSearchOption): Flow<SearchPlaceResponse> {
        return flow {
            emit(
                placeService.searchPlace(
                    category = request.category,
                    query = request.query,
                    size = request.size,
                    page = request.page,
                    minX = request.minX,
                    maxX = request.maxX,
                    minY = request.minY,
                    maxY = request.maxY,
                    disabilityType = request.disabilityType.toList(),
                    detailFilter = request.detailFilter.toList(),
                    arrange = request.arrange
                )
            )
        }
    }
}