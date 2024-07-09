package kr.tekit.lion.daongil.presentation.schedulereview.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.plan.AddNewScheduleReviewUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetBriefScheduleInfoUseCase

class WriteScheduleReviewViewModelFactory : ViewModelProvider.Factory {

    private val getBriefScheduleInfoUseCase = GetBriefScheduleInfoUseCase(
        PlanRepository.create()
    )

    private val addNewScheduleReviewUseCase = AddNewScheduleReviewUseCase(
        PlanRepository.create()
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WriteScheduleReviewViewModel::class.java)){
            return WriteScheduleReviewViewModel(
                getBriefScheduleInfoUseCase,
                addNewScheduleReviewUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}