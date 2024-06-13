package kr.tekit.lion.daongil.presentation.main.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.GetMyMainScheduleUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetOpenPlanListUseCase

class ScheduleMainViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val authRepository = AuthRepository.create(context)

    private val getOpenPlanListUseCase = GetOpenPlanListUseCase(PlanRepository.create())

    private val getMyMainScheduleUseCase = GetMyMainScheduleUseCase(PlanRepository.create())

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleMainViewModel::class.java)) {
            return ScheduleMainViewModel(getOpenPlanListUseCase, getMyMainScheduleUseCase, authRepository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}