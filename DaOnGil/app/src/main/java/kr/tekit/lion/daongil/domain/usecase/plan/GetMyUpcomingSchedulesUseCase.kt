package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.MyUpcomingSchedules
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetMyUpcomingSchedulesUseCase (
    private val planRepository: PlanRepository
): BaseUseCase() {
    suspend operator fun invoke(size: Int, page: Int) : Result<MyUpcomingSchedules> = execute {
        planRepository.getMyUpcomingScheduleList(size, page)
    }
}