package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.repository.PlaceBookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceBookmarkUseCase(
    private val placeBookmarkRepository: PlaceBookmarkRepository
) : BaseUseCase() {
    suspend operator fun invoke() : Result<List<PlaceBookmark>> = execute {
        placeBookmarkRepository.getPlaceBookmark()
    }
}