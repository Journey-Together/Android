package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.MyUpcomingSchedules
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetMyUpcomingSchedulesUseCase (
    private val planRepository: PlanRepository
): BaseUseCase() {

    companion object{
        private const val PAGE_SIZE = 10
    }

    suspend operator fun invoke(page: Int) : Result<MyUpcomingSchedules> = execute {
        planRepository.getMyUpcomingScheduleList(PAGE_SIZE, page)
    }
}