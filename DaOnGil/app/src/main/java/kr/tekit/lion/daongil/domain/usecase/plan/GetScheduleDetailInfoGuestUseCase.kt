package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.ScheduleDetail
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetScheduleDetailInfoGuestUseCase (private val planRepository: PlanRepository
) : BaseUseCase() {

    suspend operator fun invoke(planId: Long): Result<ScheduleDetail> = execute {
        planRepository.getDetailScheduleInfoGuest(planId)
    }
}