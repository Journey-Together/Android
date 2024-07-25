package kr.tekit.lion.daongil.presentation.schedule.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.AuthRepository
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailInfoGuestUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailnfoUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailUseCase
import java.lang.IllegalArgumentException

class ScheduleDetailViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val getScheduleDetailUseCase = GetScheduleDetailUseCase(
       PlanRepository.create()
    )

    private val authRepository = AuthRepository.create(context)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleDetailViewModel::class.java)){
            return ScheduleDetailViewModel(getScheduleDetailUseCase, authRepository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}