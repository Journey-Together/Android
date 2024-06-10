package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.PlanBookmark
import kr.tekit.lion.daongil.domain.repository.PlanBookmarkRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetPlanBookmarkUseCase(
    private val planBookmarkRepository: PlanBookmarkRepository
) : BaseUseCase() {

    suspend operator fun invoke() : Result<List<PlanBookmark>> = execute {
        planBookmarkRepository.getPlanBookmark()
    }
}