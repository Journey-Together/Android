package kr.tekit.lion.daongil.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.tekit.lion.daongil.data.datasource.PlaceDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.network.service.PlaceService
import kr.tekit.lion.daongil.data.repository.PlaceRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.model.ListSearchOption
import kr.tekit.lion.daongil.domain.model.MapSearchOption
import kr.tekit.lion.daongil.domain.model.MyPlaceReview
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfoGuest
import kr.tekit.lion.daongil.domain.model.PlaceMainInfo
import kr.tekit.lion.daongil.domain.model.SearchPlace

interface PlaceRepository {
    suspend fun getPlaceDetailInfo(placeId: Long): PlaceDetailInfo
    suspend fun getSearchPlaceResultForList(request: ListSearchOption): List<SearchPlace>
    fun getSearchPlaceResultForMap(request: MapSearchOption): Flow<List<SearchPlace>>
    suspend fun getPlaceDetailInfoGuest(placeId: Long): PlaceDetailInfoGuest
    suspend fun getPlaceMainInfo(areaCode: String, sigunguCode: String): PlaceMainInfo
    suspend fun getMyPlaceReview(size: Int, page: Int): List<MyPlaceReview>

    companion object{
        fun create(): PlaceRepositoryImpl{
            return PlaceRepositoryImpl(
                PlaceDataSource(
                    RetrofitInstance.serviceProvider(PlaceService::class.java)
                )
            )
        }
    }
}