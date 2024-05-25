package kr.tekit.lion.daongil.domain.usecase.touristspot

import kr.tekit.lion.daongil.domain.model.InfantFamilyTouristSpot
import kr.tekit.lion.daongil.domain.model.PhysicalDisabilityTouristSpot
import kr.tekit.lion.daongil.domain.repository.PhysicalDisabilityTouristSpotRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase

class AddPhysicalDisabilityTouristSpotUseCase(
    private val physicalDisabilityTouristSpotRepository: PhysicalDisabilityTouristSpotRepository
): BaseUseCase() {
    suspend operator fun invoke(hearingImpairmentTouristSpot: PhysicalDisabilityTouristSpot) = execute {
        physicalDisabilityTouristSpotRepository.addHearingImpairmentTouristSpot(hearingImpairmentTouristSpot)
    }
}