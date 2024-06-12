package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.data.repository.PlaceRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo

interface PlaceDetailInfoRepository {
    suspend fun getPlaceDetailInfo(placeId: Long): PlaceDetailInfo

    companion object{
        fun crate(): PlaceRepositoryImpl{
            return PlaceRepositoryImpl(
                PlaceDataSource(
                    RetrofitInstance.serviceProvider(PlaceService::class.java)
                )
            )
        }
    }
}