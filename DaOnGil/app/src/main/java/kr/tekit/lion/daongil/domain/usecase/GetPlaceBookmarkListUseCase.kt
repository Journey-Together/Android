package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.BookmarkedPlace
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlaceBookmarkListUseCase(
    private val bookmarkRepository: BookmarkRepository
) : BaseUseCase() {
    suspend operator fun invoke() : Result<List<BookmarkedPlace>> = execute {
        bookmarkRepository.getPlaceBookmarkList()
    }
}