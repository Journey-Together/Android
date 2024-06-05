package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDetailInfoDataSource
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoRepository

class PlaceDetailInfoRepositoryImpl(
    private val placeDetailInfoDataSource: PlaceDetailInfoDataSource
): PlaceDetailInfoRepository {

    override suspend fun getPlaceDetailInfo(placeId: Long): PlaceDetailInfo {
        return placeDetailInfoDataSource.getPlaceDetailInfo(placeId).toDomainModel()
    }
}