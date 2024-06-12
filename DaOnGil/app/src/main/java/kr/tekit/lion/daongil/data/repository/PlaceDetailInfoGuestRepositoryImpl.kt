package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.PlaceDetailInfoGuestDataSource
import kr.tekit.lion.daongil.domain.model.PlaceDetailInfoGuest
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoGuestRepository

class PlaceDetailInfoGuestRepositoryImpl(
    private val placeDetailInfoGuestDataSource: PlaceDetailInfoGuestDataSource
): PlaceDetailInfoGuestRepository {

    override suspend fun getPlaceDetailInfoGuest(placeId: Long): PlaceDetailInfoGuest {
        return placeDetailInfoGuestDataSource.getPlaceDetailInfoGuest(placeId).toDomainModel()
    }
}