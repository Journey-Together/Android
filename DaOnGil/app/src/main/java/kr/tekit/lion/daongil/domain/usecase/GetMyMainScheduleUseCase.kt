package kr.tekit.lion.daongil.domain.usecase

import kr.tekit.lion.daongil.domain.model.MyMainSchedule
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.base.BaseUseCase
import kr.tekit.lion.daongil.domain.usecase.base.Result

class GetMyMainScheduleUseCase(
    private val planRepository: PlanRepository
) : BaseUseCase() {

    suspend operator fun invoke(): Result<List<MyMainSchedule?>?> = execute {
        planRepository.getMyMainSchedule()
    }
}