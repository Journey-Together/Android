package kr.tekit.lion.daongil.data.repository

import kr.tekit.lion.daongil.data.datasource.touristspot.PhysicalDisabilityTouristSpotDataSource
import kr.tekit.lion.daongil.data.dto.remote.request.touristspot.toRequestModel
import kr.tekit.lion.daongil.domain.model.PhysicalDisabilityTouristSpot
import kr.tekit.lion.daongil.domain.repository.PhysicalDisabilityTouristSpotRepository

class PhysicalDisabilityTouristSpotRepositoryImpl(
    private val physicalDisabilityTouristSpotDataSource: PhysicalDisabilityTouristSpotDataSource
): PhysicalDisabilityTouristSpotRepository {

    override suspend fun addHearingImpairmentTouristSpot(touristSpot: PhysicalDisabilityTouristSpot) {
        physicalDisabilityTouristSpotDataSource.addTouristSpot(touristSpot.toRequestModel())
    }

    override suspend fun getAllHearingImpairmentTouristSpot() {
        TODO("Not yet implemented")
    }
}