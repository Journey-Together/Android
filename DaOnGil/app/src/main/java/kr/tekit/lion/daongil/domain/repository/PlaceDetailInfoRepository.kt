package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDetailInfoDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.PlaceDetailInfoRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo

interface PlaceDetailInfoRepository {
    suspend fun getPlaceDetailInfo(placeId: Long): PlaceDetailInfo

    companion object{
        fun crate(): PlaceDetailInfoRepositoryImpl{
            return PlaceDetailInfoRepositoryImpl(
                PlaceDetailInfoDataSource(
                    RetrofitInstance.placeService
                )
            )
        }
    }
}