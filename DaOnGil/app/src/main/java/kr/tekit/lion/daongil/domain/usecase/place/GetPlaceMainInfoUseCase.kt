package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.PlaceMainInfo
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result


class GetPlaceMainInfoUseCase(
    private val placeMainInfoRepository: PlaceRepository
): BaseUseCase() {

    suspend operator fun invoke(areaCode: String, sigunguCode: String): Result<PlaceMainInfo> = execute {
        placeMainInfoRepository.getPlaceMainInfo(areaCode, sigunguCode)
    }
}