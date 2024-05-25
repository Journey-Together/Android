package kr.tekit.lion.daongil.domain.repository

import kr.tekit.lion.daongil.data.datasource.touristspot.PhysicalDisabilityTouristSpotDataSource
import kr.tekit.lion.daongil.data.network.RetrofitInstance
import kr.tekit.lion.daongil.data.repository.PhysicalDisabilityTouristSpotRepositoryImpl
import kr.tekit.lion.daongil.domain.model.PhysicalDisabilityTouristSpot

interface PhysicalDisabilityTouristSpotRepository {
    suspend fun addHearingImpairmentTouristSpot(touristSpot: PhysicalDisabilityTouristSpot)
    suspend fun getAllHearingImpairmentTouristSpot()

    companion object{
        fun create(): PhysicalDisabilityTouristSpotRepositoryImpl{
            return PhysicalDisabilityTouristSpotRepositoryImpl(
                PhysicalDisabilityTouristSpotDataSource(
                    RetrofitInstance.firebaseService
                )
            )
        }
    }
}