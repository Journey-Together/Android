package kr.tekit.lion.daongil.domain.usecase.plan

import kr.tekit.lion.daongil.domain.model.MyElapsedSchedules
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetMyElapsedSchedulesUseCase (
    private val planRepository: PlanRepository
): BaseUseCase() {

    companion object{
        private const val PAGE_SIZE = 10
    }

    suspend operator fun invoke(page: Int) : Result<MyElapsedSchedules> = execute {
        planRepository.getMyElapsedScheduleList(PAGE_SIZE, page)
    }
}