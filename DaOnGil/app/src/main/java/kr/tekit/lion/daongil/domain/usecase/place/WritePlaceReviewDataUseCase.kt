package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.NewReviewData
import kr.tekit.lion.daongil.domain.model.NewReviewImages
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result
import okhttp3.ResponseBody

class WritePlaceReviewDataUseCase(
    private val placeRepository: PlaceRepository
): BaseUseCase() {
    suspend operator fun invoke(placeId: Long, newReviewData: NewReviewData, reviewImages: NewReviewImages): Result<ResponseBody> = execute {
        placeRepository.writePlaceReviewData(placeId, newReviewData, reviewImages)
    }
}