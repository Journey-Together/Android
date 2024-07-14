package kr.tekit.lion.daongil.presentation.myschedule.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.plan.GetMyElapsedSchedulesUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetMyUpcomingSchedulesUseCase

class MyScheduleViewModelFactory: ViewModelProvider.Factory  {
    private val getMyUpcomingSchedulesUseCase = GetMyUpcomingSchedulesUseCase(
        PlanRepository.create()
    )
    private val getMyElapsedSchedulesUseCase = GetMyElapsedSchedulesUseCase(
        PlanRepository.create()
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MyScheduleViewModel::class.java)){
            return MyScheduleViewModel(
                getMyUpcomingSchedulesUseCase,
                getMyElapsedSchedulesUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}