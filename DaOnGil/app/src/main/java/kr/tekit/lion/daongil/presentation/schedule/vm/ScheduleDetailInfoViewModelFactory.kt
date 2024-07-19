package kr.tekit.lion.daongil.presentation.schedule.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailInfoGuestUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailInfoUseCase
import java.lang.IllegalArgumentException

class ScheduleDetailInfoViewModelFactory(context: Context): ViewModelProvider.Factory {
    private val getScheduleDetailInfoUseCase = GetScheduleDetailInfoUseCase(
        PlanRepository.create()
    )

    private val getScheduleDetailInfoGuestUseCase = GetScheduleDetailInfoGuestUseCase(
        PlanRepository.create()
    )

    private val authRepository = AuthRepository.create(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleDetailInfoViewModel::class.java)){
            return ScheduleDetailInfoViewModel(getScheduleDetailInfoUseCase, authRepository, getScheduleDetailInfoGuestUseCase) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}