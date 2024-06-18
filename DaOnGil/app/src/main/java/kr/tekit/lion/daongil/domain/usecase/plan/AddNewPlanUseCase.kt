package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class AddNewPlanUseCase(
    private val planRepository: PlanRepository
) : BaseUseCase() {

    suspend operator fun invoke(request: NewPlan): Result<Unit> = execute {
        planRepository.addNewPlan(request)
    }
}