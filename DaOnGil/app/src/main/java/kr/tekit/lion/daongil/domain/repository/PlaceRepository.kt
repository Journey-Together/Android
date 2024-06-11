package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.data.repository.PlaceRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.model.SearchOption
import kr.tekit.lion.daongil.domain.model.SearchPlace

interface PlaceRepository {
    suspend fun getPlaceDetailInfo(placeId: Long): PlaceDetailInfo
    suspend fun getSearchPlaceResult(request: SearchOption): List<SearchPlace>

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