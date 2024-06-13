package kr.tekit.lion.daongil.domain.usecase.place

import kr.tekit.lion.daongil.domain.model.PlaceBookmark
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) : BaseUseCase() {
    suspend operator fun invoke() : Result<List<PlaceBookmark>> = execute {
        bookmarkRepository.getPlaceBookmark()
    }
}