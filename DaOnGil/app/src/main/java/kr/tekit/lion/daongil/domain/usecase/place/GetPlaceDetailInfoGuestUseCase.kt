package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.PlaceDetailInfoGuest
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceDetailInfoGuestUseCase (
    private val placeRepository: PlaceRepository
): BaseUseCase() {

    suspend operator fun invoke(placeId: Long): Result<PlaceDetailInfoGuest> = execute {
        placeRepository.getPlaceDetailInfoGuest(placeId)
    }
}