package kr.tekit.lion.daongil.presentation.scheduleform.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.BookmarkRepository
import kr.tekit.lion.daongil.domain.repository.PlaceRepository
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceBookmarkListUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceSearchResultUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.GetScheduleDetailUseCase
import kr.tekit.lion.daongil.domain.usecase.plan.ModifyScheduleUseCase

class ModifyScheduleFormViewModelFactory : ViewModelProvider.Factory {
    private val getScheduleDetailInfoUseCase = GetScheduleDetailUseCase(
        PlanRepository.create()
    )

    private val getPlaceSearchResultUseCase = GetPlaceSearchResultUseCase(
        PlanRepository.create()
    )

    private val getPlaceDetailInfoUseCase = GetPlaceDetailInfoUseCase(
        PlaceRepository.create()
    )

    private val getPlaceBookmarkListUseCase = GetPlaceBookmarkListUseCase(
        BookmarkRepository.create()
    )

    private val modifyScheduleUseCase = ModifyScheduleUseCase(
        PlanRepository.create()
    )

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ModifyScheduleFormViewModel::class.java)){
            return ModifyScheduleFormViewModel(
                getScheduleDetailInfoUseCase,
                getPlaceSearchResultUseCase,
                getPlaceDetailInfoUseCase,
                getPlaceBookmarkListUseCase,
                modifyScheduleUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}