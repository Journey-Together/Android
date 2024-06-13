package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class UpdatePlanBookmarkUseCase(
    private val bookmarkRepository: BookmarkRepository
) : BaseUseCase() {
    suspend operator fun invoke(planId: Long) : Result<Unit> = execute {
        bookmarkRepository.updatePlanBookmark(planId)
    }
}