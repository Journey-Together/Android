package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.ScheduleDetailInfo
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class UpdateMyPlanPublicUseCase(
    private val planRepository: PlanRepository
): BaseUseCase() {
    suspend operator fun invoke(planId: Long): Result<ScheduleDetailInfo> = execute {
        planRepository.updateMyPlanPublic(planId)
        planRepository.getDetailScheduleInfo(planId)
    }
}