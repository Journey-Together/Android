package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlaceMainInfoDataSource
import kr.tekit.lion.daongil.domain.model.PlaceMainInfo
import kr.tekit.lion.daongil.domain.repository.PlaceMainInfoRepository

class PlaceMainInfoRepositoryImpl(
    private val placeMainInfoDataSource: PlaceMainInfoDataSource
) : PlaceMainInfoRepository {
    override suspend fun getPlaceMainInfo(areaCode: String, sigunguCode: String): PlaceMainInfo {
        return placeMainInfoDataSource.getPlaceMainInfo(areaCode, sigunguCode).toDomainModel()
    }
}