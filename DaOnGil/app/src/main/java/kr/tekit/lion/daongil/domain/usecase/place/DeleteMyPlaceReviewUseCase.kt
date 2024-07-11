package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class DeleteMyPlaceReviewUseCase(
    private val placeRepository: PlaceRepository
): BaseUseCase() {
    suspend operator fun invoke(reviewId: Long) : Result<Unit> = execute {
        placeRepository.deleteMyPlaceReview(reviewId)
    }
}