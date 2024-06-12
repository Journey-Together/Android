package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PlaceDetailInfoGuest
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoGuestRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceDetailInfoGuestUseCase (
    private val placeDetailInfoGuestRepository: PlaceDetailInfoGuestRepository
): BaseUseCase() {
    suspend operator fun invoke(placeId: Long): Result<PlaceDetailInfoGuest> = execute {
        placeDetailInfoGuestRepository.getPlaceDetailInfoGuest(placeId)
    }
}