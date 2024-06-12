package kr.tekit.lion.daongil.presentation.scheduleform.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.tekit.lion.daongil.domain.repository.PlaceDetailInfoRepository
import kr.tekit.lion.daongil.domain.repository.PlanRepository
import kr.tekit.lion.daongil.domain.usecase.AddNewPlanUseCase
import kr.tekit.lion.daongil.domain.usecase.GetPlaceSearchResultUseCase
import kr.tekit.lion.daongil.domain.usecase.place.GetPlaceDetailInfoUseCase

// ViewModelFactory = ViewModel을 생성하는 역할을 하는 클래스
// ViewModelProvider.Factory 인터페이스를 구현한다.
class ScheduleFormViewModelFactory : ViewModelProvider.Factory {
    private val getPlaceSearchResultUseCase = GetPlaceSearchResultUseCase(
        PlanRepository.create() // repository의 인스턴스를 생성하여 전달
    )
    private val getPlaceDetailInfoUseCase = GetPlaceDetailInfoUseCase(
        PlaceDetailInfoRepository.crate()
    )
    private val addNewPlanUseCase = AddNewPlanUseCase(
        PlanRepository.create()
    )

    // create : 특정 ViewModel 클래스를 인스턴스화하는 역할
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScheduleFormViewModel::class.java)){
            return ScheduleFormViewModel(
                getPlaceSearchResultUseCase,
                getPlaceDetailInfoUseCase,
                addNewPlanUseCase
            ) as T
        }
        throw IllegalArgumentException("unknown ViewModel Class")
    }
}