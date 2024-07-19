package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetScheduleDetailInfoUseCase(
    private val planRepository: PlanRepository
) : BaseUseCase() {
    suspend operator fun invoke(planId: Long): Result<ScheduleDetail> = execute {
        planRepository.getDetailScheduleInfo(planId)
    }
}