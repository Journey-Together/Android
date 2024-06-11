package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDataSource
import kr.tekit.lion.daongil.data.dto.remote.request.toRequestModel
import kr.tekit.lion.daongil.data.dto.remote.response.searchplace.toDomainModel
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.model.SearchOption
import kr.tekit.lion.daongil.domain.model.SearchPlace
import kr.tekit.lion.daongil.domain.repository.PlaceRepository

class PlaceRepositoryImpl(
    private val placeDataSource: PlaceDataSource
): PlaceRepository {

    override suspend fun getPlaceDetailInfo(placeId: Long): PlaceDetailInfo {
        return placeDataSource.getPlaceDetailInfo(placeId).toDomainModel()
    }

    override suspend fun getSearchPlaceResult(request: SearchOption): List<SearchPlace> {
        val response = placeDataSource.searchPlace(request.toRequestModel())
        return response.toDomainModel()
    }
}