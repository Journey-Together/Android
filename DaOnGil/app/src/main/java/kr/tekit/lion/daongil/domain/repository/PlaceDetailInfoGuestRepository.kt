package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDetailInfoGuestDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.data.repository.PlaceDetailInfoGuestRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfoGuest

interface PlaceDetailInfoGuestRepository {
    suspend fun getPlaceDetailInfoGuest(placeId: Long): PlaceDetailInfoGuest

    companion object{
        fun create(): PlaceDetailInfoGuestRepositoryImpl{
            return PlaceDetailInfoGuestRepositoryImpl(
                PlaceDetailInfoGuestDataSource(
                    RetrofitInstance.serviceProvider(PlaceService::class.java)
                )
            )
        }
    }
}