package kr.tekit.lion.daongil.data.datasource.touristspot

import android.util.Log
import kr.tekit.lion.daongil.data.dto.remote.request.touristspot.PhysicalDisabilityTouristSpotRequest
import kr.tekit.lion.daongil.data.network.service.FirebaseService
import kr.tekit.lion.daongil.domain.model.PhysicalDisabilityTouristSpot

class PhysicalDisabilityTouristSpotDataSource(
    private val firebaseService: FirebaseService
) {
    suspend fun addTouristSpot(touristSpot: PhysicalDisabilityTouristSpotRequest){
        firebaseService.addPhysicalDisabilityTouristSpot(touristSpot)
    }

    suspend fun getTouristSpot(){

    }
}