package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PlanBookmark
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlanBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) : BaseUseCase() {

    suspend operator fun invoke() : Result<List<PlanBookmark>> = execute {
        bookmarkRepository.getPlanBookmark()
    }
}