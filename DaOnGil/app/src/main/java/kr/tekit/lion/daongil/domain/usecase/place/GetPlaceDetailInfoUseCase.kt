package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceDetailInfoUseCase(
    private val placeRepository: PlaceRepository
): BaseUseCase() {

    suspend operator fun invoke(placeId: Long): Result<PlaceDetailInfo> = execute {
        placeRepository.getPlaceDetailInfo(placeId)
    }
}