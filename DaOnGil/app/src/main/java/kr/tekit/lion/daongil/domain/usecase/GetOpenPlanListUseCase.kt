package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.OpenPlan
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetOpenPlanListUseCase(
    private val planRepository: PlanRepository
) : BaseUseCase() {

    suspend operator fun invoke(size: Int, page: Int): Result<OpenPlan> = execute {
        planRepository.getOpenPlanList(size, page)
    }
}