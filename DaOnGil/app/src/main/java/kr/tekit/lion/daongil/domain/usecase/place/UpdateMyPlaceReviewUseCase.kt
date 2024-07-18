package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.MyPlaceReviewImages
import kr.tekit.lion.daongil.domain.model.UpdateMyPlaceReview
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result
import okhttp3.ResponseBody

class UpdateMyPlaceReviewUseCase(
    private val placeRepository: PlaceRepository
): BaseUseCase() {
    suspend operator fun invoke(reviewId: Long, updateMyPlaceReview: UpdateMyPlaceReview, myPlaceReviewImages: MyPlaceReviewImages): Result<ResponseBody> = execute {
        placeRepository.updateMyPlaceReviewData(reviewId, updateMyPlaceReview, myPlaceReviewImages)
    }
}