package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDataSource
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoRepository

class PlaceRepositoryImpl(
    private val placeDataSource: PlaceDataSource
): PlaceDetailInfoRepository {

    override suspend fun getPlaceDetailInfo(placeId: Long): PlaceDetailInfo {
        return placeDataSource.getPlaceDetailInfo(placeId).toDomainModel()
    }

    override suspend fun getSearchPlaceResult() {
        TODO("Not yet implemented")
    }
}