package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.NewPlan
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class ModifyScheduleUseCase (
    private val planRepository: PlanRepository
) : BaseUseCase() {

    suspend operator fun invoke(planId: Long, request: NewPlan): Result<Unit> = execute {
        planRepository.modifySchedule(planId, request)
    }
}