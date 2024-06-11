package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class UpdatePlaceBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) : BaseUseCase() {
    suspend operator fun invoke(placeId: Long) : Result<Unit> = execute {
        bookmarkRepository.updatePlaceBookmark(placeId)
    }
}