package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.PlaceReviewInfo
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceReviewListUseCase(
    private val placeRepository: PlaceRepository
): BaseUseCase() {
    suspend operator fun invoke(placeId: Long, size: Int, page: Int): Result<PlaceReviewInfo> = execute {
        placeRepository.getPlaceReviewList(placeId, size, page)
    }
}