package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class DeleteMyPlanScheduleUseCase(
    private val planRepository: PlanRepository
): BaseUseCase() {
    suspend operator fun invoke(planId: Long): Result<Unit> = execute {
        planRepository.deleteMyPlanSchedule(planId)
    }
}