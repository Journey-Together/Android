package kr.tekit.lion.daongil.presentation.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.GetOpenPlanListUseCase

class ScheduleMainViewModelFactory(): ViewModelProvider.Factory {

    private val getOpenPlanListUseCase = GetOpenPlanListUseCase(PlanRepository.create())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleMainViewModel::class.java)) {
            return ScheduleMainViewModel(getOpenPlanListUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}