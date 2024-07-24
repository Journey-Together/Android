package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.PlaceDetailInfo
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceDetailInfoUseCase(
    private val placeRepository: PlaceRepository
): BaseUseCase() {

    suspend operator fun invoke(placeId: Long): Result<PlaceDetailInfo> = execute {
        val placeDetailInfo = placeRepository.getPlaceDetailInfo(placeId)

        val tel = placeDetailInfo.tel.ifEmpty { "문의 정보가 제공되지 않습니다" }
        val homePage = placeDetailInfo.homepage.ifEmpty { "홈페이지 정보가 제공되지 않습니다" }

        placeDetailInfo.copy(tel = tel, homepage = homePage)
    }
}