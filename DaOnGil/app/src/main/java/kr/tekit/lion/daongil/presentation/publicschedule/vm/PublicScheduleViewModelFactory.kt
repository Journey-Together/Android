package kr.tekit.lion.daongil.presentation.publicschedule.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.GetOpenPlanListUseCase

class PublicScheduleViewModelFactory(): ViewModelProvider.Factory {

    private val getOpenPlanListUseCase = GetOpenPlanListUseCase(PlanRepository.create())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PublicScheduleViewModel::class.java)) {
            return PublicScheduleViewModel(getOpenPlanListUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}