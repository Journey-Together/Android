package kr.tekit.lion.daongil.presentation.schedulereview.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleReviewInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.ModifyScheduleReviewUseCase

class ModifyScheduleReviewViewModelFactory : ViewModelProvider.Factory {
    private val getScheduleReviewInfoUseCase = GetScheduleReviewInfoUseCase(
        PlanRepository.create()
    )

    private val modifyScheduleReviewUseCase = ModifyScheduleReviewUseCase(
        PlanRepository.create()
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ModifyScheduleReviewViewModel::class.java)){
            return ModifyScheduleReviewViewModel(
                getScheduleReviewInfoUseCase,
                modifyScheduleReviewUseCase
            ) as T
        }
        else throw IllegalArgumentException("Unknown ViewModel Class")
    }
}