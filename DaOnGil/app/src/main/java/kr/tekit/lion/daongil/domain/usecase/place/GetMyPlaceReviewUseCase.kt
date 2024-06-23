package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.MyPlaceReview
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetMyPlaceReviewUseCase(
    private val placeRepository: PlaceRepository
) : BaseUseCase() {
    suspend operator fun invoke(size: Int, page: Int): Result<List<MyPlaceReview>> = execute {
        placeRepository.getMyPlaceReview(size, page)
    }
}