package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceDetailInfoUseCase(
    private val placeDetailInfoRepository: PlaceDetailInfoRepository
): BaseUseCase() {
    suspend operator fun invoke(placeId: Long): Result<PlaceDetailInfo> = execute {
        placeDetailInfoRepository.getPlaceDetailInfo(placeId)
    }
}